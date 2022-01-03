package ru.molefed.persister.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.molefed.persister.entity.AEntityWithId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "HWEE_USER_EMAIL_VALID_STORE")
@NoArgsConstructor
@Getter
@Setter
public class UserEmailValidStore extends AEntityWithId {

	@Column(name = "KEY_STR")
	private String key;
	private LocalDateTime created;
}
