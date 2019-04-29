package ru.molefed.db.repo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.molefed.db.entity.book.Author;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.db.repo.EntityFakeDeletedWithNameAndIdRepository;
import ru.molefed.db.repo.EntityWithNameAndIdRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long>, EntityFakeDeletedWithNameAndIdRepository<AppUser> {

}
