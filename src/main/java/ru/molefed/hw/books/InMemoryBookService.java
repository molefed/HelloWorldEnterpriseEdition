package ru.molefed.hw.books;

import org.springframework.stereotype.Service;
import ru.molefed.hw.db.entity.BookEntity;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryBookService implements BookService {
    private AtomicLong idGenerator = new AtomicLong(1);

    private static Map<Long, BookEntity> booksDB = new HashMap<>();

    static
    {
        BookEntity b = new BookEntity();
        b.setId(1L);
        b.setAuthor("Толстой");
        b.setTitle("Война и мир");
        b.setDate(new Date());

        booksDB.put(b.getId(), b);
    }

    @Override
    public List<BookEntity> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(booksDB.values()));
    }

    @Override
    public void saveAll(List<BookEntity> bookEntities) {
        long nextId = getNextId();
        Map<Long, BookEntity> bookMap = new HashMap<>();
        for (BookEntity bookEntity : bookEntities) {
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
