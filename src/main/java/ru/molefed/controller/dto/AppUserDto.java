package ru.molefed.controller.dto;

import lombok.Getter;
import lombok.Setter;
import ru.molefed.db.entity.AEntityWithNameAndId;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AppUserDto extends AEntityWithNameAndId {

	private String password;
	private Set<String> roles = new HashSet<>();
}
