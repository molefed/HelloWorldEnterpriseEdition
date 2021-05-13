package ru.molefed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.db.entity.auth.RefreshToken;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.db.repo.auth.RefreshTokenRepository;

import java.time.LocalDateTime;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public void saveNew(String token, AppUser user, LocalDateTime expiresDate) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setAppUser(user);
        refreshToken.setExpiresDate(expiresDate);
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public RefreshToken getValidToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findById(token).orElse(null);
        if (refreshToken != null) {
            if (refreshToken.getExpiresDate().isBefore(LocalDateTime.now())) {
                return refreshToken;
            }
        }
        return null;
    }

}
