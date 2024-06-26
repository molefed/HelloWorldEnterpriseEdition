package ru.molefed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.persister.entity.auth.RefreshToken;
import ru.molefed.persister.entity.user.AppUser;
import ru.molefed.persister.repository.auth.RefreshTokenRepository;
import ru.molefed.utils.DateUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;


	@Transactional
	public void saveNew(String token, AppUser user, LocalDateTime expiresDate) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(token);
		refreshToken.setAppUser(user);
		refreshToken.setExpiresDate(expiresDate);
		refreshTokenRepository.save(refreshToken);
	}

	@Transactional
	public void deleteAllUsersToken(String token) {
		refreshTokenRepository.deleteToken(token);
	}

	@Transactional(readOnly = true)
	public RefreshToken getValidToken(String token) {
		RefreshToken refreshToken = refreshTokenRepository.findById(token).orElse(null);
		if (refreshToken != null) {
			if (refreshToken.getExpiresDate().isAfter(DateUtils.now())) {
				return refreshToken;
			}
		}

		return null;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteOldTokens() {
		refreshTokenRepository.deleteOldTokens(DateUtils.now());
	}
}
