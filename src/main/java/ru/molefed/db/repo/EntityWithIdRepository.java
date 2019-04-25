package ru.molefed.db.repo;

import org.springframework.data.jpa.repository.Query;

public interface EntityWithIdRepository<T> {

    @Query("select t from #{#entityName} t where t.id = (select max(id) from #{#entityName} )")
    T getBookWithMaxId();

}
