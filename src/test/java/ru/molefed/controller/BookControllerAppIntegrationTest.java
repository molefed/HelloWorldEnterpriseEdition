package ru.molefed.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.molefed.BookApplication;
import ru.molefed.db.entity.book.Author;
import ru.molefed.db.entity.book.Book;
import ru.molefed.db.repo.book.AuthorRepository;
import ru.molefed.utils.JsonUtils;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.molefed.utils.JsonUtils.asJsonString;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public class BookControllerAppIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testSave() throws Exception {
        Author author = new Author("Толстой");
        authorRepository.save(author);

        Book b = new Book();
        b.setAuthor(author);
        b.setTitle("Гарри Потер");

        MvcResult result = mockMvc.perform(post("/books/save").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(b)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Book b1 = JsonUtils.asObject(result.getResponse().getContentAsString(), Book.class);
        assertEquals(b.getTitle(), b1.getTitle());

        authorRepository.deleteAll();
    }

}
