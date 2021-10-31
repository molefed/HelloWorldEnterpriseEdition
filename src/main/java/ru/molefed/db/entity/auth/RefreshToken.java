package ru.molefed.db.entity.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.molefed.db.entity.user.AppUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "HWEE_REFRESH_TOKEN")
@NoArgsConstructor
@Getter
@Setter
public class RefreshToken {

	@Id
	@Column(name = "TOKEN")
	private String token;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private AppUser appUser;
	@Column(name = "EXPIRES_DATE")
	private LocalDateTime expiresDate;
}
