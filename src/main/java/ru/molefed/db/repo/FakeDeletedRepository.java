package ru.molefed.db.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.molefed.db.entity.EntityFakeDeletedWithId;
import ru.molefed.db.entity.book.Book;

import java.util.List;

public interface FakeDeletedRepository<T extends EntityFakeDeletedWithId> extends BaseFakeDeletedRepository<T> {

    @Query("select t from #{#entityName} t where t.deleted = ?1")
    List<T> findMarked(Boolean deleted);

    List<Book> findByDeleted(boolean deleted, Pageable page);

    List<Book> findByDeleted(boolean deleted);

}