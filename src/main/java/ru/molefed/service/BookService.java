package ru.molefed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.molefed.db.entity.book.Author;
import ru.molefed.db.entity.book.Book;
import ru.molefed.db.repo.book.AuthorRepository;
import ru.molefed.db.repo.book.BookRepository;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public List<Book> getAll(Integer page, Integer size) {

        List<Book> result = bookRepository.findByDeleted(false, PageRequest.of(page, size, Sort.Direction.ASC, "id"));

        return result;
    }

    @Transactional
    public Book saveBooks(Book resource) {
        Author author = authorRepository.findById(resource.getAuthor().getId()).get();
        resource.setAuthor(author);
        Book book = bookRepository.save(resource);
        return book;
    }

    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
