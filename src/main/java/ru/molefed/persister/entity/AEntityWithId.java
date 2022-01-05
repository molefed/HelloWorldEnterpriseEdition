package ru.molefed.persister.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class AEntityWithId implements EntityWithId {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //TODO переделать на сиквенс или UUID
	protected Long id;

	@Override
	public int hashCode() {
		return getId() == null ? super.hashCode() : getId().intValue();
	}

	@Override
	public boolean equals(Object that) {
		if (that == null) {
			return false;
		}

		if (getClass() != that.getClass()) {
			return false;
		}

		if (getId() == null) {
			return super.equals(that);
		}

		EntityWithId entityWithId = (EntityWithId) that;
		return getId().equals(entityWithId.getId());
	}
}
