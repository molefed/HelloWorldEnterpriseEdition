package ru.molefed.controller.dto;

import lombok.Getter;
import lombok.Setter;
import ru.molefed.db.entity.AEntityWithNameAndId;

@Getter
@Setter
public class SignInResponseTO extends RefreshTokenResponseTO {

	private AEntityWithNameAndId user;
	private String refreshToken;
	protected long refreshExpiresIn;
}
