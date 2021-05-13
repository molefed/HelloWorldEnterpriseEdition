package ru.molefed.db.entity.auth;

import ru.molefed.db.entity.user.AppUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "HWEE_REFRESH_TOKEN")
public class RefreshToken {
    @Id
    @Column(name = "TOKEN")
    private String token;
    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID")
    private AppUser appUser;
    @Column(name = "EXPIRES_DATE")
    private LocalDateTime expiresDate;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public LocalDateTime getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(LocalDateTime expiresDate) {
        this.expiresDate = expiresDate;
    }

}
