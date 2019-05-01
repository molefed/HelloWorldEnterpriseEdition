package ru.molefed.db.entity.user;

import ru.molefed.db.entity.AEntityWithNameAndId;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AppRole"/*,
        uniqueConstraints = {
                @UniqueConstraint(name = "AppRoleNameUK", columnNames = AEntityWithNameAndId.NAME) }*/)
public class AppRole extends AEntityWithNameAndId {

    @ManyToMany(mappedBy = "roles")
    private Set<AppUser> users = new HashSet<>();

    public AppRole() {

    }

    public AppRole(String name) {
        super(name);
    }

    public Set<AppUser> getUsers() {
        return users;
    }

}
