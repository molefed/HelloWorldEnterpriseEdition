package ru.molefed.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequestTO {

	private String username;
	private String password;
}
