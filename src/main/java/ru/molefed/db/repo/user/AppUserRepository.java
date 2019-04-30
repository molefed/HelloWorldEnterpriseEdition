package ru.molefed.db.repo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.db.repo.EntityFakeDeletedWithNameAndIdRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long>, EntityFakeDeletedWithNameAndIdRepository<AppUser> {

}
