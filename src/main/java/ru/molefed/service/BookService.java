package ru.molefed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.controller.dto.BookDto;
import ru.molefed.persister.entity.book.Author;
import ru.molefed.persister.entity.book.Book;
import ru.molefed.persister.repository.book.AuthorRepository;
import ru.molefed.persister.repository.book.BookRepository;
import ru.molefed.utils.EntityWithIdUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;

	public List<BookDto> getAll(Integer page, Integer size) {

		List<BookDto> result = new ArrayList<>();
		List<Book> list = bookRepository.findByDeleted(false, PageRequest.of(page, size, Sort.Direction.ASC, "id"));
		for (Book b : list) {
			result.add(BookDto.toDto(b));
		}

		return result;
	}

	@Transactional
	public BookDto saveBooks(BookDto bookDto) {
		Book book = bookDto.getId() == null ? new Book() : bookRepository.findById(bookDto.getId()).get();
		book.setId(bookDto.getId());
		book.setTitle(bookDto.getTitle());
		book.setDate(bookDto.getDate());
		book.setPrice(bookDto.getPrice());

		if (!EntityWithIdUtils.isEmptyId(bookDto.getAuthor()) &&
				!EntityWithIdUtils.equalsId(bookDto.getAuthor(), book.getAuthor())) {
			Author author = authorRepository.findById(bookDto.getAuthor().getId()).get();
			book.setAuthor(author);
		}

		bookRepository.save(book);
		return BookDto.toDto(book);
	}

	@Transactional
	public void delete(Long id) {
		bookRepository.deleteById(id);
	}
}
