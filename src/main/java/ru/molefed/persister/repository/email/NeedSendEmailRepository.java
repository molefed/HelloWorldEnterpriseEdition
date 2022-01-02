package ru.molefed.persister.repository.email;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.molefed.persister.entity.email.NeedSendEmail;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Set;

@Repository
public interface NeedSendEmailRepository extends JpaRepository<NeedSendEmail, Long> {

	@Modifying
	@Query("DELETE FROM NeedSendEmail WHERE id in :ids")
	void deleteIn(@Param("ids") Set<Long> ids);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<NeedSendEmail> findByOrderByCreated(Pageable pageable);
}
