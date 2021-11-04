package ru.molefed.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.molefed.controller.dto.RefreshTokenRequestTO;
import ru.molefed.controller.dto.RefreshTokenResponseTO;
import ru.molefed.controller.dto.SignInRequestTO;
import ru.molefed.controller.dto.SignInResponseTO;
import ru.molefed.controller.mapper.AuthMapper;
import ru.molefed.persister.entity.auth.RefreshToken;
import ru.molefed.persister.entity.user.AppUser;
import ru.molefed.security.JwtProvider;
import ru.molefed.service.RefreshTokenService;
import ru.molefed.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth", method = RequestMethod.POST)
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	private final JwtProvider jwtProvider;
	private final AuthMapper authMapper;
	private final RefreshTokenService refreshTokenService;

	@Transactional
	@RequestMapping("/generateToken")
	public SignInResponseTO generateToken(@RequestBody @Valid SignInRequestTO requestTO) {
		AppUser appUser = userService.get(requestTO.getUsername());
		if (appUser == null || !userService.isPasswordValid(appUser, requestTO.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}

		checkUserValid(appUser);

		JwtProvider.TokenInfo tokenInfo = jwtProvider.generateToken(appUser);
		JwtProvider.TokenInfo refreshTokenInfo = jwtProvider.generateAndSaveRefreshToken(appUser);

		userService.updateLastLogin(appUser.getName());

		return authMapper.signin(appUser,
								 tokenInfo.getToken(),
								 tokenInfo.getExpiresInSec(),
								 refreshTokenInfo.getToken(),
								 refreshTokenInfo.getExpiresInSec());
	}

	@Transactional
	@RequestMapping("/refreshToken")
	public RefreshTokenResponseTO refreshToken(@RequestBody @Valid RefreshTokenRequestTO refreshTokenRequestTO) {
		RefreshToken refreshToken = refreshTokenService.getValidToken(refreshTokenRequestTO.getToken());

		if (refreshToken == null) {
			throw new BadCredentialsException("Invalid refresh token");
		}

		checkUserValid(refreshToken.getAppUser());

		JwtProvider.TokenInfo tokenInfo = jwtProvider.generateToken(refreshToken.getAppUser());

		userService.updateLastLogin(refreshToken.getAppUser().getName());

		return authMapper.refrershTokenResponseTO(
				tokenInfo.getToken(),
				tokenInfo.getExpiresInSec());
	}

	@RequestMapping("/signout")
	public void signout(@RequestBody @Valid RefreshTokenRequestTO refreshTokenRequestTO) {
		refreshTokenService.deleteAllUsersToken(refreshTokenRequestTO.getToken());
	}

	private void checkUserValid(AppUser appUser) {
		if (appUser.isDeleted()) {
			throw new BadCredentialsException("User deleted");
		}
	}
}
