package ru.molefed.db.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.molefed.db.entity.AEntityWithNameAndId;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "HWEE_ROLE"/*,
        uniqueConstraints = {
                @UniqueConstraint(name = "AppRoleNameUK", columnNames = AEntityWithNameAndId.NAME) }*/)
@NoArgsConstructor
@Getter
public class AppRole extends AEntityWithNameAndId {

	@ManyToMany(mappedBy = "roles")
	private final Set<AppUser> users = new HashSet<>();
}
