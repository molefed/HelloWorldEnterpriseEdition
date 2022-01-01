package ru.molefed.persister.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.molefed.persister.entity.EntityFakeDeletedWithId;

import java.util.List;

public interface FakeDeletedRepository<T extends EntityFakeDeletedWithId> extends BaseFakeDeletedRepository<T> {

    @Query("select t from #{#entityName} t where t.deleted = ?1")
    List<T> findMarked(Boolean deleted);

    List<T> findByDeleted(boolean deleted, Pageable page);

    List<T> findByDeleted(boolean deleted);

}