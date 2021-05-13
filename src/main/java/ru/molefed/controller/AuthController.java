package ru.molefed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.molefed.controller.dto.RefreshTokenRequestTO;
import ru.molefed.controller.dto.RefreshTokenResponseTO;
import ru.molefed.controller.dto.SignInRequestTO;
import ru.molefed.controller.dto.SignInResponseTO;
import ru.molefed.controller.mapper.AuthMapper;
import ru.molefed.db.entity.auth.RefreshToken;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.security.JwtProvider;
import ru.molefed.service.RefreshTokenService;
import ru.molefed.service.UserService;

@RestController
@RequestMapping(value = "/auth", method = RequestMethod.POST)
public class AuthController {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthMapper authMapper;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(UserService userService, JwtProvider jwtProvider, AuthMapper authMapper, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.authMapper = authMapper;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    @RequestMapping("/generateToken")
    public SignInResponseTO signin(@RequestBody SignInRequestTO requestTO) {
        AppUser appUser = userService.get(requestTO.getUsername());
        if (appUser == null || !userService.isPasswordValid(appUser, requestTO.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        checkUserValid(appUser);

        JwtProvider.TokenInfo tokenInfo = jwtProvider.generateToken(appUser);
        JwtProvider.TokenInfo refreshTokenInfo = jwtProvider.generateRefreshToken(appUser);

        return authMapper.signin(appUser,
                tokenInfo.getToken(),
                tokenInfo.getExpiresInSec(),
                refreshTokenInfo.getToken(),
                refreshTokenInfo.getExpiresInSec());
    }

    @Transactional(readOnly = true)
    @RequestMapping("/refreshToken")
    public RefreshTokenResponseTO refreshToken(@RequestBody RefreshTokenRequestTO refreshTokenRequestTO) {
        RefreshToken refreshToken = refreshTokenService.getValidToken(refreshTokenRequestTO.getToken());

        if (refreshToken == null) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        checkUserValid(refreshToken.getAppUser());

        JwtProvider.TokenInfo tokenInfo = jwtProvider.generateToken(refreshToken.getAppUser());

        return authMapper.refrershTokenResponseTO(
                tokenInfo.getToken(),
                tokenInfo.getExpiresInSec());
    }

    private void checkUserValid(AppUser appUser) {
        if (appUser.isDeleted()) {
            throw new BadCredentialsException("User deleted");
        }
    }

}
