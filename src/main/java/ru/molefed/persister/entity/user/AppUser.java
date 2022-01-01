package ru.molefed.persister.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.molefed.persister.entity.AEntityFakeDeletedWithNameAndId;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "HWEE_USER")
@NoArgsConstructor
@Getter
@Setter
public class AppUser extends AEntityFakeDeletedWithNameAndId {

	private String encrytedPassword;
	private String email;
	private String firstName;
	private String lastName;
	private LocalDate birthday;
	@Enumerated(EnumType.ORDINAL)
	private Gender gender;
	private LocalDateTime lastLogin;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "HWEE_USER_ROLE",
			joinColumns = @JoinColumn(name = "USER_ID"),
			inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
	)
	private Set<AppRole> roles;
}
