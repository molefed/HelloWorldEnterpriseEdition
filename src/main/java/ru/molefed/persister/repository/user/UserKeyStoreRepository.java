package ru.molefed.persister.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.molefed.persister.entity.user.UserKeyStore;
import ru.molefed.persister.entity.user.UserKeyStoreType;

import java.time.LocalDateTime;

@Repository
public interface UserKeyStoreRepository extends JpaRepository<UserKeyStore, Long> {

	UserKeyStore findByKeyAndType(String key, UserKeyStoreType type);

	@Modifying
	@Query("DELETE FROM UserKeyStore s WHERE s.created < :createdDateLess AND" +
			" EXISTS (SELECT u.id FROM AppUser u WHERE u.id = s.id AND u.validEmail = true)")
	void removeOld(@Param(value = "createdDateLess") LocalDateTime createdDateLess);
}
