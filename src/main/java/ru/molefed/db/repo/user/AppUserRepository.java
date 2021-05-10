package ru.molefed.db.repo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.db.repo.EntityFakeDeletedWithNameAndIdRepository;

import java.time.LocalDateTime;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>, EntityFakeDeletedWithNameAndIdRepository<AppUser> {

    @Modifying
    @Query("UPDATE AppUser SET lastLogin=:lastLogin WHERE name = :userName")
    void updateLastLogin(@Param("userName") String userName,
                         @Param("lastLogin") LocalDateTime lastLogin);

}
