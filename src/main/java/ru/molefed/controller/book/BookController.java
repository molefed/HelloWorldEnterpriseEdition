package ru.molefed.controller.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.molefed.db.entity.book.Author;
import ru.molefed.db.entity.book.Book;
import ru.molefed.db.repo.book.AuthorRepository;
import ru.molefed.db.repo.book.BookRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping(value = "/all", params = { "page", "size" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Book> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {

        List<Book> result = bookRepository.findByDeleted(false, PageRequest.of(page, size, Sort.Direction.ASC, "id"));

        return result;
    }

    @PostMapping(value = "/save")
    public Book saveBooks(@RequestBody final Book resource) {
        Author author = authorRepository.findById(resource.getAuthor().getId()).get();
        resource.setAuthor(author);
        Book book = bookRepository.save(resource);

        return book;
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        bookRepository.deleteById(id);
    }


}
