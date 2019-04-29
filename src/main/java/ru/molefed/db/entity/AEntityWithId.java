package ru.molefed.db.entity;

import javax.persistence.*;

@MappedSuperclass
public class AEntityWithId implements EntityWithId {
    public static final String ID = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = AEntityWithId.ID, length = 20, unique = true, nullable = false)
    protected Long id;

    public AEntityWithId() {

    }

    public AEntityWithId(Long id) {
        setId(id);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return getId() == null ? super.hashCode() : getId().intValue();
    }

    @Override
    public boolean equals(Object that) {
        if (getId() == null || that == null || !(that instanceof EntityWithId))
            return super.equals(that);

        EntityWithId entityWithId = (EntityWithId) that;
        return getId().equals(entityWithId.getId());
    }


}
