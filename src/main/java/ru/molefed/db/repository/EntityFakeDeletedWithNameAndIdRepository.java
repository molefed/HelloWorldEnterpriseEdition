package ru.molefed.db.repository;

import ru.molefed.db.entity.EntityFakeDeletedWithId;

public interface EntityFakeDeletedWithNameAndIdRepository<T extends EntityFakeDeletedWithId> extends EntityWithNameAndIdRepository<T>, FakeDeletedRepository<T> {

}
