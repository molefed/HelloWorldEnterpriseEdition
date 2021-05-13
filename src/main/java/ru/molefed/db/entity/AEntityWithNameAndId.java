package ru.molefed.db.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AEntityWithNameAndId extends AEntityWithId implements EntityWithNameAndId {
    public static final String NAME = "name";

    protected String name;

    protected AEntityWithNameAndId() {

    }

    public AEntityWithNameAndId(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
