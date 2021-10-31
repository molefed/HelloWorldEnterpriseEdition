package ru.molefed.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenResponseTO {

	private String token;
	protected long expiresIn;
}
