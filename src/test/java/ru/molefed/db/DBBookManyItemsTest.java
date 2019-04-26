package ru.molefed.db;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.BookApplication;
import ru.molefed.db.entity.book.Author;
import ru.molefed.db.entity.book.Book;
import ru.molefed.db.repo.book.AuthorRepository;
import ru.molefed.db.repo.book.BookRepository;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {BookApplication.class})
@Transactional
public class DBBookManyItemsTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Before
    public void setUp() {
        Author author = new Author("Толстой");
        for (int i = 1; i <= 100; i++) {
            Book b = new Book();
            b.setAuthor(author);
            b.setTitle("Война и мир" + i);
            b.setDate(Time.valueOf(LocalTime.now()));

            bookRepository.save(b);
        }
    }

    @Test
    public void testPagination() {
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
    public void testFindUseMethod() {
        Author author = authorRepository.findByName("Толстой");
        Book b = bookRepository.findByAuthor_idAndTitle(author.getId(), "Война и мир35").get();
        assertNotNull(b);
        assertEquals("Война и мир35", b.getTitle());

        List<Book> list = bookRepository.findByTitleStartsWith("Войн",
                PageRequest.of(0, 500));
        assertEquals(100, list.size());
    }

    @Test
    public void testCustomRepository() {
        Book b = bookRepository.getBookWithMaxId();
        assertNotNull(b);
        assertTrue(b.getId() >= 100);
    }

    @Test
    public void testFindDeleted() {
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

