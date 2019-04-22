package ru.molefed.hw.books;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    void saveAll(List<Book> books);

    void remove(Long id);
}
