package ru.molefed.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class AEntityWithNameAndId extends AEntityWithId implements EntityWithNameAndId {

	public static final String NAME = "name";

	protected String name;
}
