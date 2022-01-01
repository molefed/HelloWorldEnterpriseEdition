package ru.molefed.controller.dto;

import lombok.Getter;
import lombok.Setter;
import ru.molefed.persister.entity.AEntityWithNameAndId;
import ru.molefed.persister.entity.user.Gender;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class AppUserDto extends AEntityWithNameAndId {

	private String password;

	@NotBlank
	private String email;

	private String firstName;
	private String lastName;
	private LocalDate birthday;
	private Gender gender;
	private LocalDateTime lastLogin;
	private Set<String> roles;
}
