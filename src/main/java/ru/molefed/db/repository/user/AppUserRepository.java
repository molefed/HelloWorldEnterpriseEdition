package ru.molefed.db.repository.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.db.repository.EntityFakeDeletedWithNameAndIdRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>, EntityFakeDeletedWithNameAndIdRepository<AppUser> {

    @Modifying
    @Query("UPDATE AppUser SET lastLogin=:lastLogin WHERE name = :userName")
    void updateLastLogin(@Param("userName") String userName,
                         @Param("lastLogin") LocalDateTime lastLogin);

    @Query("select u from AppUser u join fetch u.roles WHERE u.name like :pattern")
    List<AppUser> search(@Param("pattern") String pattern, Pageable pageable);

}
