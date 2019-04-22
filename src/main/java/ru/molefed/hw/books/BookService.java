package ru.molefed.hw.books;

import ru.molefed.hw.db.entity.BookEntity;

import java.util.List;

public interface BookService {

    List<BookEntity> findAll();

    void saveAll(List<BookEntity> bookEntities);

    void remove(Long id);
}
