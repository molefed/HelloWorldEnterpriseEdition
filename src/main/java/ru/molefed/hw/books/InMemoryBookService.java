package ru.molefed.hw.books;

import org.springframework.stereotype.Service;
import ru.molefed.hw.db.entity.Book;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryBookService implements BookService {
    private AtomicLong idGenerator = new AtomicLong(1);

    private static Map<Long, Book> booksDB = new HashMap<>();

    static
    {
        Book b = new Book();
        b.setId(1L);
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
    public void saveAll(List<Book> bookEntities) {
        long nextId = getNextId();
        Map<Long, Book> bookMap = new HashMap<>();
        for (Book bookEntity : bookEntities) {
            if (bookEntity.getId() == 0) {
                bookEntity.setId(nextId++);
            }

            bookMap.put(bookEntity.getId(), bookEntity);
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
