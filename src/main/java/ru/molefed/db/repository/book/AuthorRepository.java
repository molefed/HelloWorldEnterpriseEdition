package ru.molefed.db.repository.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.molefed.db.entity.book.Author;
import ru.molefed.db.repository.EntityWithNameAndIdRepository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, EntityWithNameAndIdRepository<Author> {

}