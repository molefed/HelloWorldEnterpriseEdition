package ru.molefed.persister.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.molefed.persister.entity.AEntityWithNameAndId;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "HWEE_ROLE")
@NoArgsConstructor
@Getter
public class AppRole extends AEntityWithNameAndId {

	@ManyToMany(mappedBy = "roles")
	private final Set<AppUser> users = new HashSet<>();
}
