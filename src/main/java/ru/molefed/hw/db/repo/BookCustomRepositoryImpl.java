package ru.molefed.hw.db.repo;

import org.springframework.transaction.annotation.Transactional;
import ru.molefed.hw.db.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BookCustomRepositoryImpl implements BookCustomRepository<Book> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book getBookWithMaxId() {
        return em.createQuery("from Book where id = (select max(id) from Book )", Book.class)
                .getSingleResult();
    }

    @Transactional
    @Override
    public void delete(Book entity) {
        Book employees = (Book) entity;
        employees.setDeleted(true);
        em.persist(employees);
    }

}
