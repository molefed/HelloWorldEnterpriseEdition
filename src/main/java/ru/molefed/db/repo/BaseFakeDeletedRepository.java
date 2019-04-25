package ru.molefed.db.repo;

import ru.molefed.db.entity.EntityFakeDeletedWithId;

public interface BaseFakeDeletedRepository<T extends EntityFakeDeletedWithId> {

    void delete(T entity);

}