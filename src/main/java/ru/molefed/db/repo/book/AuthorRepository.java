package ru.molefed.db.repo.book;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.molefed.db.entity.book.Author;
import ru.molefed.db.repo.EntityWithNameAndIdRepository;

public interface AuthorRepository extends JpaRepository<Author, Long>, EntityWithNameAndIdRepository<Author> {

}
