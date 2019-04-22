package ru.molefed.hw.db.entity;

import org.springframework.format.annotation.DateTimeFormat;
import ru.molefed.hw.books.CatalogType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Book")
public class BookEntity {
    @Id
    @Column(name="id")
    private Long id;
    @Column(name="title")
    private String title;
    @Column(name="author")
    private String author;
    @Column(name="date")
    private Date date;
    private CatalogType catalogType = CatalogType.OPEN;
    private boolean pub = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
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

    private CatalogType getCatalogType() {
        return catalogType;
    }

    private void setCatalogType(CatalogType catalogType) {
        this.catalogType = catalogType;
    }

    public boolean getPub() {
        return !CatalogType.CLOSE.equals(getCatalogType());
    }

    public void setPub(boolean pub) {
        this.pub = pub;
        setCatalogType(pub ? CatalogType.OPEN : CatalogType.CLOSE);
    }

}
