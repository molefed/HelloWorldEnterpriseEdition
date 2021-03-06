package ru.molefed.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.molefed.BookApplication;
import ru.molefed.Roles;
import ru.molefed.db.entity.AEntityWithId;
import ru.molefed.db.entity.book.Author;
import ru.molefed.db.entity.book.Book;
import ru.molefed.db.repo.book.AuthorRepository;
import ru.molefed.db.repo.book.BookRepository;
import ru.molefed.dto.BookDto;
import ru.molefed.utils.DateUtils;
import ru.molefed.utils.JsonUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.molefed.utils.JsonUtils.asJsonString;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@ContextConfiguration
public class BookControllerAppIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    private Date date = DateUtils.asDate(LocalDate.of(2015, 02, 20));

    @Test
    @WithMockUser(username = "misha", authorities = {Roles.USER})
    public void test() throws Exception {
        save();
        allAndUpdate();
        delete();

        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    public void save() throws Exception {
        Author author = new Author("Толстой");
        authorRepository.save(author);

        BookDto b = new BookDto();
        b.setAuthor(new AEntityWithId(author.getId()));
        b.setTitle("Гарри Потер");
        b.setDate(date);
        b.setPrice(32.2);

        MvcResult result = mockMvc.perform(post("/books/save").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(b)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        BookDto b1 = JsonUtils.asObject(result.getResponse().getContentAsString(), BookDto.class);
        assertBook(b, author);
    }

    private void assertBook(BookDto b, Author author) {
        assertEquals(b.getTitle(), "Гарри Потер");
        assertEquals(b.getDate(), date);
        assertEquals(b.getPrice(), 32.2, 0.01);
        assertEquals(b.getAuthor().getId(), author.getId());
    }

    public void allAndUpdate() throws Exception {
        MvcResult result = mockMvc.perform(get("/books/all").contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> list = JsonUtils.asList(result.getResponse().getContentAsString(), BookDto.class);
        assertEquals(1, list.size());
        BookDto b = list.get(0);
        assertBook(b, authorRepository.findAll().get(0));

        Author author = new Author("Пушкин");
        authorRepository.save(author);
        b.setAuthor(author);
        b.setTitle("Сказка о царе Солтане");

        result = mockMvc.perform(post("/books/save").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(b)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        BookDto b1 = JsonUtils.asObject(result.getResponse().getContentAsString(), BookDto.class);
        assertEquals(b1.getTitle(), "Сказка о царе Солтане");
        assertEquals(b1.getDate(), date);
        assertEquals(b1.getPrice(), 32.2, 0.01);
        assertEquals(b1.getAuthor().getId(), author.getId());
    }

    public void delete() throws Exception {
        List<Book> books = bookRepository.findAll();
        assertEquals(1, books.size());

        mockMvc.perform(get("/books/delete/" + books.get(0).getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertTrue(bookRepository.findAll().isEmpty());
    }

}
