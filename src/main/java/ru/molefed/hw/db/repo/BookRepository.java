package ru.molefed.hw.db.repo;

import org.springframework.data.repository.CrudRepository;

import ru.molefed.hw.db.entity.BookEntity;

public interface BookRepository extends CrudRepository<BookEntity, Long> {

}
