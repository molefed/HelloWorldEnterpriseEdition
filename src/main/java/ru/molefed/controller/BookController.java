package ru.molefed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.molefed.Roles;
import ru.molefed.dto.BookDto;
import ru.molefed.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Secured(Roles.USER)
    @GetMapping(value = "/all", params = {"page", "size"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<BookDto> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return bookService.getAll(page, size);
    }

    @Secured(Roles.USER)
    @PostMapping(value = "/save")
    public BookDto saveBooks(@RequestBody BookDto bookDto) {
        return bookService.saveBooks(bookDto);
    }

    @Secured(Roles.USER)
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        bookService.delete(id);
    }


}
