package ru.molefed.persister.entity.email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.molefed.persister.entity.AEntityWithId;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "HWEE_NEED_SEND_EMAIL")
@NoArgsConstructor
@Getter
@Setter
public class NeedSendEmail extends AEntityWithId {

	private String title;
	private String body;
	private String email;
	private LocalDateTime created;
}
