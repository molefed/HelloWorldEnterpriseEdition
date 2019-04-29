package ru.molefed.db.entity;

public interface EntityWithNameAndId extends EntityWithId {

    String getName();

    void setName(String name);

}
