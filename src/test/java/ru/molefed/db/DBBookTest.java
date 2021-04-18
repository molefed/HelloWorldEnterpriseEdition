package ru.molefed.db;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.BookApplication;
import ru.molefed.db.entity.book.Author;
import ru.molefed.db.entity.book.Book;
import ru.molefed.db.entity.book.CatalogType;
import ru.molefed.db.repo.book.AuthorRepository;
import ru.molefed.db.repo.book.BookRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {BookApplication.class})
@Transactional
public class DBBookTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    private final LocalDate date = LocalDate.of(2015, 2, 20);

    @Test
    @Commit // если не ставим, тест ниже провалится, логично
    public void testMaping() {
        Author author = new Author("Толстой");
        Book book = new Book();
        book.setAuthor(author);
        book.setTitle("Война и мир");
        book.setPrice(651.9867);
        book.setCatalogType(CatalogType.CLOSE);
        book.setDate(date);
        assertNull(book.getId());
        assertEquals(0, author.getBooks().size());

        book = bookRepository.save(book);
        assertNotNull(book.getId());
        assertEquals(0, author.getBooks().size()); // видимо в коллекцию нужно руками складывать если нужно до коммита

        Book b1 = bookRepository.findById(book.getId()).get();

        Assert.assertEquals("Толстой", b1.getAuthor().getName());
        assertEquals("Война и мир", b1.getTitle());
        assertEquals(651.9867, b1.getPrice(), 0.00001);
        Assert.assertEquals(CatalogType.CLOSE, b1.getCatalogType());
        assertEquals(date, b1.getDate());

        b1.setCatalogType(CatalogType.OPEN);
        author = b1.getAuthor();
        author.setName("Tolstoi");
        b1.setAuthor(author);
        assertTrue(b1 == book); // юзает кэш
//        b = bookRepository.save(b1);

        book = bookRepository.findById(book.getId()).get(); // юзает кэш
        Assert.assertEquals("Tolstoi", b1.getAuthor().getName());
        Assert.assertEquals(CatalogType.OPEN, b1.getCatalogType());

        bookRepository.save(b1);
    }

    @Test
    @Commit // это нужно чтобы deleteAll ниже почувствовали следующие тесты, почему?
    public void testCheckAfterCommit() {
        List<Book> list = bookRepository.findAll();
        assertEquals(1, list.size());

        Book b = list.get(0);

        Assert.assertEquals("Tolstoi", b.getAuthor().getName());
        assertEquals("Война и мир", b.getTitle());
        assertEquals(651.9867, b.getPrice(), 0.00001);
        Assert.assertEquals(CatalogType.OPEN, b.getCatalogType());
        assertEquals(date, b.getDate());

        List<Author> authors = authorRepository.findAll();
        assertEquals(1, authors.size());
        assertEquals(1, authors.get(0).getBooks().size());
        assertTrue(authors.get(0).getBooks().contains(b));

        bookRepository.deleteAll(); // более цивилизовано нужно сделать
        authorRepository.deleteAll();
    }
}

