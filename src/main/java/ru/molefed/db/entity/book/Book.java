package ru.molefed.db.entity.book;

import org.springframework.format.annotation.DateTimeFormat;
import ru.molefed.db.entity.AEntityFakeDeletedWithId;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book extends AEntityFakeDeletedWithId {
    @Column(name="title", length=128, nullable = false)
    private String title;
    @OneToOne(cascade = {CascadeType.ALL}) // разобраться почему сохранение без этого не пашет
    @JoinColumn(name="author_id", nullable=false)
    private Author author;
    @Column(name="date")
    private Date date;
    @Column(name="price", scale = 18, precision = 4)
    private Double price;
    @Column(name="catalogType")
    @Enumerated(EnumType.ORDINAL)
    private CatalogType catalogType = CatalogType.OPEN;

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

    @DateTimeFormat(pattern="dd/mm/yyyy")
    public Date getDate() {
        return date;
    }

    @DateTimeFormat(pattern="dd/mm/yyyy")
    public void setDate(Date date) {
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
