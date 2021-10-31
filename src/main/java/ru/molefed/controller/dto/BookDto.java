package ru.molefed.controller.dto;

import lombok.Getter;
import lombok.Setter;
import ru.molefed.db.entity.AEntityWithId;
import ru.molefed.db.entity.book.Book;

import java.time.LocalDate;

@Getter
@Setter
public class BookDto extends AEntityWithId {

	private String title;
	private LocalDate date;
	private Double price;
	private AEntityWithId author;

	public static BookDto toDto(Book book) {
		BookDto result = new BookDto();
		result.setId(book.getId());
		result.setTitle(book.getTitle());
		result.setDate(book.getDate());
		result.setPrice(book.getPrice());

		if (book.getAuthor() != null && book.getAuthor().getId() != null) {
			AEntityWithId author = new AEntityWithId();
			author.setId(book.getAuthor().getId());

			result.setAuthor(author);
		}

		return result;
	}
}
