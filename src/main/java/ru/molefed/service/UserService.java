package ru.molefed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.db.repo.user.AppUserRepository;
import ru.molefed.utils.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;

	public List<AppUser> getAll(Integer page, Integer size) {
		return appUserRepository.findByDeleted(false,
											   PageRequest.of(page, size, Sort.Direction.ASC, "id"));
	}

	public List<AppUser> search(String pattern) {
		String searchPattern = StringUtils.isEmpty(pattern) ? "%" : "%" + pattern + "%";

		return appUserRepository.search(searchPattern,
										PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")));
	}

	public AppUser get(long id) {
		return appUserRepository.findById(id).get();
	}

	public AppUser get(String name) {
		return appUserRepository.findByName(name);
	}

	public boolean isPasswordValid(AppUser user, String password) {
		return passwordEncoder.matches(password, user.getEncrytedPassword());
	}

	@Transactional
	public AppUser save(AppUser user, String password) {
		if (!StringUtils.isEmpty(password)) {
			user.setEncrytedPassword(passwordEncoder.encode(password));
		}

		return appUserRepository.save(user);
	}

	@Transactional
	public void delete(long id) {
		appUserRepository.deleteById(id);
	}

	@Transactional
	public void updateLastLogin(String userName) {
		LocalDateTime lastLogin = LocalDateTime.now();
		appUserRepository.updateLastLogin(userName, lastLogin);
	}
}
