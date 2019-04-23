package ru.molefed.hw.books;

import ru.molefed.hw.db.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    void saveAll(List<Book> bookEntities);

    void remove(Long id);
}
