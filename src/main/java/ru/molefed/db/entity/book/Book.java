package ru.molefed.db.entity.book;

import ru.molefed.db.entity.AEntityFakeDeletedWithId;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "HWEE_BOOK")
public class Book extends AEntityFakeDeletedWithId {
    @Column(name = "title", length = 128, nullable = false)
    private String title;
    @ManyToOne(cascade = CascadeType.ALL /*{CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}*/)
    @JoinColumn(name = "author_id")
    private Author author;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "price", scale = 18, precision = 4)
    private Double price;
    @Column(name = "CATALOG_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private CatalogType catalogType = CatalogType.OPEN;

    public Book() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CatalogType getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(CatalogType catalogType) {
        this.catalogType = catalogType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
