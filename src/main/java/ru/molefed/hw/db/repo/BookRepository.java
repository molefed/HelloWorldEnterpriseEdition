package ru.molefed.hw.db.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.molefed.hw.db.entity.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookCustomRepository<Book> {

    Optional<Book> findByAuthorAndTitle(String author, String title);

    List<Book> findByAuthorStartsWith(String authorStartsWith, Pageable page);

    List<Book> findByDeleted(boolean deleted, Pageable page);

    List<Book> findByDeleted(boolean deleted);

}
