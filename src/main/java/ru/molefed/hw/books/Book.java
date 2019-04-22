package ru.molefed.hw.books;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Book {

    private long id;
    private String title;
    private String author;
    private Date date;
    private CatalogType catalogType = CatalogType.OPEN;
    private boolean pub = true;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
