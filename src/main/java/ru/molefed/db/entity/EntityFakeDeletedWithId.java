package ru.molefed.db.entity;

public interface EntityFakeDeletedWithId extends EntityWithId {

    boolean isDeleted();

    void setDeleted(boolean deleted);

}
