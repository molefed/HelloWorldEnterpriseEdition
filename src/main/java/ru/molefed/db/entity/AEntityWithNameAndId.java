package ru.molefed.db.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AEntityWithNameAndId extends AEntityWithId implements EntityWithNameAndId {
    @Column(name="name", length=128, nullable = false)
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
