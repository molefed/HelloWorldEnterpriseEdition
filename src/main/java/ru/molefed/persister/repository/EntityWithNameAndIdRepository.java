package ru.molefed.persister.repository;

import ru.molefed.persister.entity.EntityWithId;

public interface EntityWithNameAndIdRepository<T extends EntityWithId> extends EntityWithIdRepository<T> {

    T findByName(String name);

}
