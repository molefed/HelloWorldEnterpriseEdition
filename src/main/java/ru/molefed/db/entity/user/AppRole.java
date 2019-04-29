package ru.molefed.db.entity.user;

import ru.molefed.db.entity.AEntityWithNameAndId;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Set;

@Entity
@Table(name = "AppRole",
        uniqueConstraints = {
                @UniqueConstraint(name = "AppRoleNameUK", columnNames = AEntityWithNameAndId.NAME) })
public class AppRole extends AEntityWithNameAndId {

    @OneToMany(mappedBy = "roles")
    private Set<AppUser> users;

    public AppRole() {

    }

    public Set<AppUser> getUsers() {
        return users;
    }

}
