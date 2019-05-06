package ru.molefed.db.entity.user;

import ru.molefed.db.entity.AEntityFakeDeletedWithNameAndId;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = AppUser.TABLE/*,
        uniqueConstraints = {
                @UniqueConstraint(name = "AppUserNameUK", columnNames = AEntityWithNameAndId.NAME) }*/)
public class AppUser extends AEntityFakeDeletedWithNameAndId {
    public static final String TABLE = "AppUser";
    public static final String LAST_LOGIN = "lastLogin";

    @Column(name = "encrytedPassword", length = 128, nullable = false)
    private String encrytedPassword;
    @Column(name = AppUser.LAST_LOGIN)
    private Date lastLogin;
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
        return Collections.unmodifiableSet(roles);
    }

    public boolean addRole(AppRole role) {
        return roles.add(role);
    }

    public boolean removeRole(AppRole role) {
        return roles.remove(role);
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

}
