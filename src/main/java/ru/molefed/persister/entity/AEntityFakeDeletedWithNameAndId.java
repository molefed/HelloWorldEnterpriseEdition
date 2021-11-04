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
public abstract class AEntityFakeDeletedWithNameAndId extends AEntityWithNameAndId implements EntityFakeDeletedWithNameAndId {

	@Column(name = "DELETED", nullable = false)
	private boolean deleted;
}
