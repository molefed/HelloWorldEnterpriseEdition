package ru.molefed.db.repo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.db.entity.AEntityWithNameAndId;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.db.repo.EntityFakeDeletedWithNameAndIdRepository;

import java.time.LocalDateTime;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>, EntityFakeDeletedWithNameAndIdRepository<AppUser> {

//    @Query("UPDATE " + AppUser.TABLE + " SET " + AppUser.LAST_LOGIN + "=:last_login WHERE " +
//            AEntityWithNameAndId.NAME + " = ?#{ principal?.username }")
//    @Modifying
//    @Transactional
//    public void updateLastLogin(@Param("last_login") LocalDateTime lastLogin);

}
