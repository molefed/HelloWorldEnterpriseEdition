package ru.molefed.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.persister.entity.user.AppUser;
import ru.molefed.persister.entity.user.UserKeyStore;
import ru.molefed.persister.entity.user.UserKeyStoreType;
import ru.molefed.persister.repository.user.AppUserRepository;
import ru.molefed.persister.repository.user.UserKeyStoreRepository;
import ru.molefed.property.AppProperty;
import ru.molefed.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final NeedSendEmailService needSendEmailService;
	private final UserKeyStoreRepository userKeyStoreRepository;
	private final TemplateService templateService;
	private final AppProperty appProperty;

	public List<AppUser> getAll(Integer page, Integer size) {
		return appUserRepository.findByDeleted(false,
											   PageRequest.of(page, size, Sort.Direction.ASC, "id"));
	}

	public List<AppUser> search(String pattern) {
		String searchPattern = StringUtils.isBlank(pattern) ? "%" : "%" + pattern + "%";

		return appUserRepository.search(searchPattern,
										PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")));
	}

	public AppUser get(long id) {
		return appUserRepository.findById(id).orElse(null);
	}

	public AppUser get(String name) {
		return appUserRepository.findByName(name.toLowerCase());
	}

	public boolean isPasswordValid(AppUser user, String password) {
		return passwordEncoder.matches(password, user.getEncrytedPassword());
	}

	@Transactional
	public AppUser save(AppUser user, String password) {
		checkDuplicate(user);

		boolean newUser = user.getId() == null;

		if (newUser) {
			user.setCreated(DateUtils.now());
		}

		if (StringUtils.isNotBlank(password)) {
			user.setEncrytedPassword(obtainEncrytedPassword(password));
		}

		if (newUser) {
			sendValidEmail(user);
		}

		return appUserRepository.save(user);
	}

	private String obtainEncrytedPassword(String password) {
		return passwordEncoder.encode(password);
	}

	private void checkDuplicate(AppUser user) {
		AppUser existUser = appUserRepository.findByNameIgnoreCaseOrEmailIgnoreCase(user.getName(), user.getEmail());

		if (existUser != null) {
			if (user.getId() == null || !user.getId().equals(existUser.getId())) {
				throw new RuntimeException("User with the same name or email already exists");
			}
		}
	}

	private UserKeyStore saveUserKeyStore(AppUser user, UserKeyStoreType type) {
		UserKeyStore userKeyStore = new UserKeyStore();
		userKeyStore.setUser(user);
		userKeyStore.setKey(RandomStringUtils.random(128, true, true));
		userKeyStore.setCreated(DateUtils.now());
		userKeyStore.setType(type);
		user.setUserKeyStore(userKeyStore);

		return userKeyStoreRepository.save(userKeyStore);
	}

	private void sendValidEmail(AppUser user) {
		var userKeyStore = saveUserKeyStore(user, UserKeyStoreType.VALID_EMAIL);

		var emailParams = Map.of("user", user.getName(),
								 "url", appProperty.getUrl() +
										 "/api/v1/users/valid-email/" +
										 userKeyStore.getKey());

		needSendEmailService.save(user.getEmail(), EmailTemplate.VALID_EMAIL, emailParams);
	}

	@Transactional
	public void delete(long id) {
		AppUser appUser = appUserRepository.findById(id).orElseThrow();
		appUser.setDeleted(true);
		appUserRepository.save(appUser);
	}

	@Transactional
	public void updateLastLogin(String userName) {
		LocalDateTime lastLogin = DateUtils.now();
		appUserRepository.updateLastLogin(userName.toLowerCase(), lastLogin);
	}

	@Transactional
	public String validEmailAndGetRedirectPage(String key) {
		var userEmailValidStore = userKeyStoreRepository.findByKeyAndType(key, UserKeyStoreType.VALID_EMAIL);
		if (userEmailValidStore == null) {
			throw new RuntimeException("Email verification key not found");
		}

		var appUser = appUserRepository.findById(userEmailValidStore.getUser().getId()).orElseThrow();
		if (appUser.isValidEmail()) {
			throw new RuntimeException(appUser.getEmail() + " already validate");
		}

		appUser.setValidEmail(true);
		appUserRepository.save(appUser);

		return templateService.getFilledTemplate("verified_email_page",
												 Map.of("email", appUser.getEmail(),
														"url", appProperty.getUrl()));
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void removeOldUserEmailValidStore() {
		userKeyStoreRepository.removeOld(DateUtils.now().minusWeeks(1));
	}

	@Transactional
	public void recovery(String email) {
		AppUser user = appUserRepository.findByEmail(email);
		if (user == null) {
			throw new RuntimeException("User not found by email " + email);
		}

		var userKeyStore = saveUserKeyStore(user, UserKeyStoreType.ACCOUNT_RECOVERY);

		Map<String, String> emailParams = Map.of("user", user.getName(),
												 "url", appProperty.getUrl() +
														 "/setup-password-by-key?key=" +
														 userKeyStore.getKey());

		needSendEmailService.save(user.getEmail(), EmailTemplate.RECOVERY, emailParams);
	}

	@Transactional
	public AppUser setupPasswordByKey(String key, String password) {
		UserKeyStore userKeyStore = userKeyStoreRepository.findByKeyAndType(key, UserKeyStoreType.ACCOUNT_RECOVERY);
		if (userKeyStore == null) {
			throw new RuntimeException("User not found by key " + key);
		}

		AppUser user = userKeyStore.getUser();
		user.setEncrytedPassword(obtainEncrytedPassword(password));

		return appUserRepository.save(user);
	}
}
