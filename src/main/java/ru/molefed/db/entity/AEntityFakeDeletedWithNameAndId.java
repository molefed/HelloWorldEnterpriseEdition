package ru.molefed.db.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AEntityFakeDeletedWithNameAndId extends AEntityWithNameAndId implements EntityFakeDeletedWithNameAndId {
    @Column(name = "DELETED", nullable = false)
    private boolean deleted;

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
