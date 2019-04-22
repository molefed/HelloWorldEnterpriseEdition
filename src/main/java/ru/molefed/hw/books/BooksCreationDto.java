package ru.molefed.hw.books;

import ru.molefed.hw.db.entity.BookEntity;

import java.util.ArrayList;
import java.util.List;

public class BooksCreationDto {

    private List<BookEntity> bookEntities;

    public BooksCreationDto() {
        this.bookEntities = new ArrayList<>();
    }

    public BooksCreationDto(List<BookEntity> bookEntities) {
        this.bookEntities = bookEntities;
    }

    public List<BookEntity> getBookEntities() {
        return bookEntities;
    }

    public void setBookEntities(List<BookEntity> bookEntities) {
        this.bookEntities = bookEntities;
    }

    public void addBook(BookEntity bookEntity) {
        this.bookEntities.add(bookEntity);
    }
}
