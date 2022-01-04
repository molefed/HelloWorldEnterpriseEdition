package ru.molefed.persister.entity.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.molefed.persister.entity.AEntityWithNameAndId;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "HWEE_BOOK")
@NoArgsConstructor
@Getter
@Setter
public class Book extends AEntityWithNameAndId {

	private boolean deleted;
	private String title;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumn(name = "author_id")
	private Author author;
	private LocalDate date;
	private Double price;
	@Column(name = "CATALOG_TYPE")
	@Enumerated(EnumType.ORDINAL)
	private CatalogType catalogType = CatalogType.OPEN;
}
