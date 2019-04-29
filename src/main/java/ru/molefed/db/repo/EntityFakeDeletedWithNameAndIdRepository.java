package ru.molefed.db.repo;

import ru.molefed.db.entity.EntityFakeDeletedWithId;

public interface EntityFakeDeletedWithNameAndIdRepository<T extends EntityFakeDeletedWithId> extends EntityWithNameAndIdRepository<T>, FakeDeletedRepository<T> {

}
