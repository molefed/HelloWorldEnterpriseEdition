package ru.molefed.db.repo;

import org.springframework.transaction.annotation.Transactional;
import ru.molefed.db.entity.EntityFakeDeletedWithId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseFakeDeletedRepositoryImpl<T extends EntityFakeDeletedWithId> implements BaseFakeDeletedRepository<T> {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void delete(T entity) {
        entity.setDeleted(true);
        em.persist(entity);
    }
}
