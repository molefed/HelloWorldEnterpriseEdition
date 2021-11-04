package ru.molefed.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RefreshTokenRequestTO {

	@NotBlank
	private String token;
}
