package ru.molefed.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.molefed.controller.dto.RefreshTokenResponseTO;
import ru.molefed.controller.dto.SignInResponseTO;
import ru.molefed.persister.entity.user.AppUser;

@Mapper(uses = {AppUserMapper.class})
public abstract class AuthMapper {

	@Mapping(target = "user", qualifiedByName = "toLiteUser")
	public abstract SignInResponseTO signin(AppUser user, String token, long expiresIn, String refreshToken, long refreshExpiresIn);

	public abstract RefreshTokenResponseTO refrershTokenResponseTO(String token, long expiresIn);
}
