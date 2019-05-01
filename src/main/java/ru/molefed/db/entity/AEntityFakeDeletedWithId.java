package ru.molefed.db.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AEntityFakeDeletedWithId extends AEntityWithId implements EntityFakeDeletedWithId {

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
