package ru.molefed.persister.entity;

public interface EntityFakeDeletedWithId extends EntityWithId {

    boolean isDeleted();

    void setDeleted(boolean deleted);

}
