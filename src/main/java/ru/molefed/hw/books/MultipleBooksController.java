package ru.molefed.hw.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.molefed.hw.db.entity.Book;
import ru.molefed.hw.db.repo.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class MultipleBooksController {

//    @Autowired
//    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping(value = "/all")
    public String showAll(Model model) {
//        model.addAttribute("books", bookService.findAll());

        model.addAttribute("books", bookRepository.findAll());

        return "allBooks";
    }

    @GetMapping(value = "/create")
    public String showCreateForm(Model model) {
        BooksCreationDto booksForm = new BooksCreationDto();

        booksForm.addBook(new Book());

        model.addAttribute("form", booksForm);

        return "createBooksForm";
    }

    @GetMapping(value = "/edit")
    public String showEditForm(Model model) {
        List<Book> bookEntities = new ArrayList<>();
//        bookService.findAll()
//            .iterator()
//            .forEachRemaining(bookEntities::add);

        bookRepository.findAll()
                .iterator()
                .forEachRemaining(bookEntities::add);

        model.addAttribute("form", new BooksCreationDto(bookEntities));

        return "editBooksForm";
    }

    @PostMapping(value = "/save")
    public String saveBooks(@ModelAttribute BooksCreationDto form, Model model) {
//        bookService.saveAll(form.getBookEntities());
//        model.addAttribute("books", bookService.findAll());

        bookRepository.saveAll(form.getBooks());
        model.addAttribute("books", bookRepository.findAll());

        return "redirect:/books/all";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam(name="id", required=true) Long id, Model model) {

//        bookService.remove(id);
//        model.addAttribute("books", bookService.findAll());

        bookRepository.deleteById(id);
        model.addAttribute("books", bookRepository.findAll());

        return "redirect:/books/all";
    }


}
