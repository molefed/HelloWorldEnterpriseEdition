package ru.molefed.persister;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.BookApplication;
import ru.molefed.persister.entity.book.Author;
import ru.molefed.persister.entity.book.Book;
import ru.molefed.persister.repository.book.AuthorRepository;
import ru.molefed.persister.repository.book.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {BookApplication.class})
@Transactional

//https://stackoverflow.com/questions/34617152/how-to-re-create-database-before-each-test-in-spring
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class DBBookManyItemsTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    public void setUp() {
        LocalDate now = LocalDate.now();
        Author author = new Author("Толстой");
        IntStream.range(1, 101).mapToObj(i -> {
            Book b = new Book();
            b.setAuthor(author);
            b.setTitle("Война и мир" + i);
            b.setDate(now);
            return b;
        }).forEach(bookRepository::save);
    }

    @Test
    void pagination() {
        Page<Book> result = bookRepository.findAll(
                PageRequest.of(0, 5, Sort.Direction.DESC, "id"));

        AtomicInteger index = new AtomicInteger(100);
        checkPage(result, index);

        while (result.hasNext()) {
            result = bookRepository.findAll(result.nextPageable());
            checkPage(result, index);
        }
    }

    private void checkPage(Page<Book> result, AtomicInteger index) {
        assertEquals(5, result.getContent().size());

        for (Book b : result.getContent()) {
            assertEquals("Война и мир" + index.get(), b.getTitle());
            index.decrementAndGet();
        }
    }

    @Test
    void findUseMethod() {
        Author author = authorRepository.findByName("Толстой");
        Book b = bookRepository.findByAuthor_idAndTitle(author.getId(), "Война и мир35").get();
        assertNotNull(b);
        assertEquals("Война и мир35", b.getTitle());

        List<Book> list = bookRepository.findByTitleStartsWith("Войн",
                PageRequest.of(0, 500));
        assertEquals(100, list.size());
    }

    @Test
    void customRepository() {
        Book b = bookRepository.getBookWithMaxId();
        assertNotNull(b);
        assertTrue(b.getId() >= 100);
    }

    @Test
    void findDeleted() {
        assertEquals(100, bookRepository.findByDeleted(false).size());

        Book b = bookRepository.getBookWithMaxId();
        b.setDeleted(true);
        bookRepository.save(b);
        assertEquals(1, bookRepository.findByDeleted(true).size());

        List<Book> list = bookRepository.findByDeleted(false);
        assertEquals(99, list.size());
        for (Book bb : list)
            bb.setDeleted(true);
        bookRepository.saveAll(list);
        assertEquals(100, bookRepository.findByDeleted(true).size());
        assertEquals(0, bookRepository.findByDeleted(false).size());

        assertEquals(bookRepository.findByDeleted(true), bookRepository.findMarked(true));
        assertEquals(bookRepository.findByDeleted(true), bookRepository.findAll());

    }

}

