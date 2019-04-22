package ru.molefed.hw.books;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryBookService implements BookService {
    private AtomicLong idGenerator = new AtomicLong(1);

    private static Map<Long, Book> booksDB = new HashMap<>();

    static
    {
        Book b = new Book();
        b.setId(1);
        b.setAuthor("Толстой");
        b.setTitle("Война и мир");
        b.setDate(new Date());

        booksDB.put(b.getId(), b);
    }

    @Override
    public List<Book> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(booksDB.values()));
    }

    @Override
    public void saveAll(List<Book> books) {
        long nextId = getNextId();
        Map<Long, Book> bookMap = new HashMap<>();
        for (Book book : books) {
            if (book.getId() == 0) {
                book.setId(nextId++);
            }

            bookMap.put(book.getId(), book);
        }

        booksDB.putAll(bookMap);
    }

    @Override
    public void remove(Long id) {
        booksDB.remove(id);
    }

    private Long getNextId() {
        return idGenerator.incrementAndGet();
    }
}
