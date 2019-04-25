package ru.molefed.db.repo.book;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.molefed.db.entity.book.Book;
import ru.molefed.db.repo.EntityWithIdRepository;
import ru.molefed.db.repo.FakeDeletedRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, FakeDeletedRepository<Book>, EntityWithIdRepository<Book> {

    Optional<Book> findByAuthor_idAndTitle(Long authorId, String title);

    List<Book> findByTitleStartsWith(String authorStartsWith, Pageable page);

}
