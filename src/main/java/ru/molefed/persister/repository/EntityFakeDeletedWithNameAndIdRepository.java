package ru.molefed.persister.repository;

import ru.molefed.persister.entity.EntityFakeDeletedWithId;

public interface EntityFakeDeletedWithNameAndIdRepository<T extends EntityFakeDeletedWithId> extends EntityWithNameAndIdRepository<T>, FakeDeletedRepository<T> {

}
