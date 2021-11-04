package ru.molefed.persister.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.molefed.persister.entity.auth.RefreshToken;

import java.time.LocalDateTime;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    @Modifying
    @Query("DELETE FROM RefreshToken WHERE expiresDate < :currentDate")
    void deleteOldTokens(@Param("currentDate") LocalDateTime currentDate);

    @Modifying
    @Query("DELETE FROM RefreshToken WHERE token = :token")
    void deleteToken(@Param("token") String token);

}
