package ru.molefed.persister.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "HWEE_USER_EMAIL_VALID_STORE")
@NoArgsConstructor
@Getter
@Setter
public class UserEmailValidStore {

	@Id
	@Column(name = "USR_ID")
	private Long id;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USR_ID")
	private AppUser user;

	@Column(name = "KEY_STR")
	private String key;
	private LocalDateTime created;

	public void setUser(AppUser user) {
		this.user = user;
		this.id = user.getId();
	}
}
