package ru.molefed.persister.repository;

import org.springframework.data.jpa.repository.Query;
import ru.molefed.persister.entity.EntityWithId;

public interface EntityWithIdRepository<T extends EntityWithId> {

    @Query("select t from #{#entityName} t where t.id = (select max(id) from #{#entityName} )")
    T getBookWithMaxId();

}
