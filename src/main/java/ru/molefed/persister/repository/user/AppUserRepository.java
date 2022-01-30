package ru.molefed.persister.repository.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.molefed.persister.entity.user.AppUser;
import ru.molefed.persister.repository.EntityWithNameAndIdRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>, EntityWithNameAndIdRepository<AppUser> {

	@Modifying
	@Query("UPDATE AppUser SET lastLogin=:lastLogin WHERE name = :userName")
	void updateLastLogin(@Param("userName") String userName,
						 @Param("lastLogin") LocalDateTime lastLogin);

	@Query("select u from AppUser u join fetch u.roles WHERE u.name like :pattern")
	List<AppUser> search(@Param("pattern") String pattern, Pageable pageable);

	List<AppUser> findByDeleted(boolean deleted, Pageable page);

	AppUser findByNameIgnoreCaseOrEmailIgnoreCase(String name, String email);

	AppUser findByEmail(String email);

}
