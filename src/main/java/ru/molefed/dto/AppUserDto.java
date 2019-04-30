package ru.molefed.dto;

import ru.molefed.db.entity.AEntityWithNameAndId;
import ru.molefed.db.entity.user.AppRole;
import ru.molefed.db.entity.user.AppUser;

import java.util.Collections;
import java.util.Set;

public class AppUserDto extends AEntityWithNameAndId {

    private String password;
    private Set<String> roles;

    public AppUserDto() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public boolean addRole(String role) {
        return roles.add(role);
    }

    public boolean removeRole(String role) {
        return roles.remove(role);
    }

    public static AppUserDto toDto(AppUser user) {
        AppUserDto result = new AppUserDto();
        result.setId(user.getId());
        result.setName(user.getName());

        for (AppRole role : user.getRoles())
            result.addRole(role.getName());

        return result;
    }

}