package ru.molefed.controller.dto;

import ru.molefed.db.entity.AEntityWithId;
import ru.molefed.db.entity.book.Book;

import java.time.LocalDate;

public class BookDto extends AEntityWithId {

    private String title;
    private LocalDate date;
    private Double price;
    private AEntityWithId author;

    public BookDto() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public AEntityWithId getAuthor() {
        return author;
    }

    public void setAuthor(AEntityWithId author) {
        this.author = author;
    }

    public static BookDto toDto(Book book) {
        BookDto result = new BookDto();
        result.setId(book.getId());
        result.setTitle(book.getTitle());
        result.setDate(book.getDate());
        result.setPrice(book.getPrice());

        if (book.getAuthor() != null && book.getAuthor().getId() != null)
            result.setAuthor(new AEntityWithId(book.getAuthor().getId()));

        return result;
    }

}
