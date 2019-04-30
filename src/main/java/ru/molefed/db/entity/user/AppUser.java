package ru.molefed.db.entity.user;

import ru.molefed.db.entity.AEntityFakeDeletedWithNameAndId;
import ru.molefed.db.entity.AEntityWithNameAndId;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "AppUser"/*,
        uniqueConstraints = {
                @UniqueConstraint(name = "AppUserNameUK", columnNames = AEntityWithNameAndId.NAME) }*/)
public class AppUser extends AEntityFakeDeletedWithNameAndId {

    @Column(name = "encrytedPassword", length = 128, nullable = false)
    private String encrytedPassword;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }) // TODO: 30.04.2019 разобраться с каскадами
    @JoinTable(name = UserRole.TABLE,
            joinColumns = @JoinColumn(name = UserRole.USER_ID),
            inverseJoinColumns = @JoinColumn(name = UserRole.ROLE_ID)
    )
    private Set<AppRole> roles;

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




}
