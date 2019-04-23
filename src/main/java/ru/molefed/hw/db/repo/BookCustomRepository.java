package ru.molefed.hw.db.repo;

import ru.molefed.hw.db.entity.Book;

public interface BookCustomRepository<T> {

    Book getBookWithMaxId();

    void delete(T entity);

}
