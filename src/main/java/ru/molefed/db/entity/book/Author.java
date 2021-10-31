package ru.molefed.db.entity.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.molefed.db.entity.AEntityWithNameAndId;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "HWEE_AUTHOR")
@NoArgsConstructor
@Getter
@Setter
public class Author extends AEntityWithNameAndId {

	@JsonIgnore
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL /*{CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}*/)
	private Set<Book> books = new HashSet<>(0);
}
