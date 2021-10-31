package ru.molefed.db.entity.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.molefed.db.entity.AEntityFakeDeletedWithId;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "HWEE_BOOK")
@NoArgsConstructor
@Getter
@Setter
public class Book extends AEntityFakeDeletedWithId {

	@Column(name = "title", length = 128, nullable = false)
	private String title;
	@ManyToOne(cascade = CascadeType.ALL /*{CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}*/)
	@JoinColumn(name = "author_id")
	private Author author;
	@Column(name = "date")
	private LocalDate date;
	@Column(name = "price", scale = 18, precision = 4)
	private Double price;
	@Column(name = "CATALOG_TYPE")
	@Enumerated(EnumType.ORDINAL)
	private CatalogType catalogType = CatalogType.OPEN;
}
