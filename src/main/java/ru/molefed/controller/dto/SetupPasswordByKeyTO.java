package ru.molefed.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SetupPasswordByKeyTO {

	@NotBlank
	private String key;

	@NotBlank
	private String password;
}
