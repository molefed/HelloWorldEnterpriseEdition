package ru.molefed.db.repository.book;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.molefed.db.entity.book.Book;
import ru.molefed.db.repository.EntityWithIdRepository;
import ru.molefed.db.repository.FakeDeletedRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, FakeDeletedRepository<Book>, EntityWithIdRepository<Book> {

    Optional<Book> findByAuthor_idAndTitle(Long authorId, String title);

    List<Book> findByTitleStartsWith(String authorStartsWith, Pageable page);

}
