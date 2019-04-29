package ru.molefed.db.repo;

import ru.molefed.db.entity.EntityWithId;

public interface EntityWithNameAndIdRepository<T extends EntityWithId> extends EntityWithIdRepository<T> {

    T findByName(String name);

}
