package ru.molefed.persister.repository.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.molefed.persister.entity.book.Author;
import ru.molefed.persister.repository.EntityWithNameAndIdRepository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, EntityWithNameAndIdRepository<Author> {

}
