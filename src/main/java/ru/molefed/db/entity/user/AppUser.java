package ru.molefed.db.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.molefed.db.entity.AEntityFakeDeletedWithNameAndId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "HWEE_USER")
@NoArgsConstructor
@Getter
@Setter
public class AppUser extends AEntityFakeDeletedWithNameAndId {

	@Column(nullable = false)
	private String encrytedPassword;

	@Column(nullable = false)
	private String email;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private LocalDateTime birthday;

	@Column
	@Enumerated(EnumType.ORDINAL)
	private Gender gender;

	@Column
	private LocalDateTime lastLogin;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "HWEE_USER_ROLE",
			joinColumns = @JoinColumn(name = "USER_ID"),
			inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
	)
	private Set<AppRole> roles = new HashSet<>();
}
