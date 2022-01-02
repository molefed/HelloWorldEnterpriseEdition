package ru.molefed.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.persister.entity.user.AppUser;
import ru.molefed.persister.repository.user.AppUserRepository;
import ru.molefed.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final NeedSendEmailService needSendEmailService;

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
		return appUserRepository.findByName(name);
	}

	public boolean isPasswordValid(AppUser user, String password) {
		return passwordEncoder.matches(password, user.getEncrytedPassword());
	}

	@Transactional
	public AppUser save(AppUser user, String password) {
		if (StringUtils.isNotBlank(password)) {
			user.setEncrytedPassword(passwordEncoder.encode(password));
		}

		user = appUserRepository.save(user);

		sendValidEmail(user);

		return user;
	}

	private void sendValidEmail(AppUser user) {
		UUID uuid = UUID.randomUUID();

		Map<String, String> emailParams = Map.of("user", user.getName(),
												 "url", "http://localhost:9090/valid-email?uuid=" + uuid);

		needSendEmailService.save(user.getEmail(), EmailTemplate.VALID_EMAIL, emailParams);
	}

	@Transactional
	public void delete(long id) {
		appUserRepository.deleteById(id);
	}

	@Transactional
	public void updateLastLogin(String userName) {
		LocalDateTime lastLogin = DateUtils.now();
		appUserRepository.updateLastLogin(userName, lastLogin);
	}
}
