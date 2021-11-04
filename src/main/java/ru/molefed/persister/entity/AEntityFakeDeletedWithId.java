package ru.molefed.persister.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public abstract class AEntityFakeDeletedWithId extends AEntityWithId implements EntityFakeDeletedWithId {

	@Column(name = "deleted", nullable = false)
	private boolean deleted = false;
}
