package ru.molefed.db.entity.user;

import ru.molefed.db.entity.AEntityFakeDeletedWithNameAndId;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = AppUser.TABLE/*,
        uniqueConstraints = {
                @UniqueConstraint(name = "AppUserNameUK", columnNames = AEntityWithNameAndId.NAME) }*/)
public class AppUser extends AEntityFakeDeletedWithNameAndId {
    public static final String TABLE = "HWEE_USER";
    public static final String LAST_LOGIN = "LAST_LOGIN";

    @Column(name = "ENCRYTED_PASSWORD", length = 128, nullable = false)
    private String encrytedPassword;
    @Column(name = AppUser.LAST_LOGIN)
    private LocalDateTime lastLogin;
    @ManyToMany( cascade = CascadeType.ALL ) // TODO: 30.04.2019 разобраться с каскадами
    @JoinTable(name = UserRole.TABLE,
            joinColumns = @JoinColumn(name = UserRole.USER_ID),
            inverseJoinColumns = @JoinColumn(name = UserRole.ROLE_ID)
    )
    private Set<AppRole> roles = new HashSet<>();

    public AppUser() {

    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

    public Set<AppRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<AppRole> roles) {
        this.roles = roles;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

}
