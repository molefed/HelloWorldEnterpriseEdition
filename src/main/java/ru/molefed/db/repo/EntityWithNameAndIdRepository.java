package ru.molefed.db.repo;

public interface EntityWithNameAndIdRepository<T> extends EntityWithIdRepository<T> {

    T findByName(String name);

}
