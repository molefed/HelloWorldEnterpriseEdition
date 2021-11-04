package ru.molefed.persister.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class AEntityWithNameAndId extends AEntityWithId implements EntityWithNameAndId {

	public static final String NAME = "name";

	@NotBlank
	protected String name;
}
