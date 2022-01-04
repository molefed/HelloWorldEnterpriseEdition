package ru.molefed.persister.repository.book;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.molefed.persister.entity.book.Book;
import ru.molefed.persister.repository.EntityWithNameAndIdRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, EntityWithNameAndIdRepository<Book> {

	Optional<Book> findByAuthor_idAndTitle(Long authorId, String title);

	List<Book> findByTitleStartsWith(String authorStartsWith, Pageable page);

	List<Book> findByDeleted(boolean deleted, Pageable page);
}
