package ru.molefed.db.entity.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.molefed.db.entity.AEntityWithNameAndId;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Author")
public class Author extends AEntityWithNameAndId {

    @JsonIgnore
    @OneToMany(mappedBy="author", cascade = {CascadeType.ALL}) // разобраться почему сохранение без каскада не пашет
    private Set<Book> books = new HashSet<>();

    public Author(){

    }

    public Author(String name) {
        super(name);
    }

    public Set<Book> getBooks() {
        return books;
    }

}
