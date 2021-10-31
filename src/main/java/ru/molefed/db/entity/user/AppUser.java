package ru.molefed.db.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.molefed.db.entity.AEntityFakeDeletedWithNameAndId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = AppUser.TABLE/*,
        uniqueConstraints = {
                @UniqueConstraint(name = "AppUserNameUK", columnNames = AEntityWithNameAndId.NAME) }*/)
@NoArgsConstructor
@Getter
@Setter
public class AppUser extends AEntityFakeDeletedWithNameAndId {

	public static final String TABLE = "HWEE_USER";
	public static final String LAST_LOGIN = "LAST_LOGIN";

	@Column(name = "ENCRYTED_PASSWORD", length = 128, nullable = false)
	private String encrytedPassword;
	@Column(name = AppUser.LAST_LOGIN)
	private LocalDateTime lastLogin;
	@ManyToMany(cascade = CascadeType.ALL) // TODO: 30.04.2019 разобраться с каскадами
	@JoinTable(name = UserRole.TABLE,
			joinColumns = @JoinColumn(name = UserRole.USER_ID),
			inverseJoinColumns = @JoinColumn(name = UserRole.ROLE_ID)
	)
	private Set<AppRole> roles = new HashSet<>();
}
