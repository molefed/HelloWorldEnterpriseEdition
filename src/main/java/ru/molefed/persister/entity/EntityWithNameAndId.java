package ru.molefed.persister.entity;

public interface EntityWithNameAndId extends EntityWithId {

    String getName();

    void setName(String name);

}
