package ru.molefed.persister.repository;

import ru.molefed.persister.entity.EntityFakeDeletedWithId;

public interface BaseFakeDeletedRepository<T extends EntityFakeDeletedWithId> {

    void delete(T entity);

}
