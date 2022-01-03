package ru.molefed.persister.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.molefed.persister.entity.user.UserEmailValidStore;

import java.time.LocalDateTime;

@Repository
public interface UserEmailValidStoreRepository extends JpaRepository<UserEmailValidStore, Long> {

	UserEmailValidStore findByKey(String key);

	@Modifying
	@Query("DELETE FROM UserEmailValidStore s WHERE s.created < :createdDateLess AND" +
			" EXISTS (SELECT u.id FROM AppUser u WHERE u.id = s.id AND u.validEmail = true)")
	void removeOld(LocalDateTime createdDateLess);
}
