package ru.molefed.controller.dto;

import ru.molefed.db.entity.AEntityWithNameAndId;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AppUserDto extends AEntityWithNameAndId {

    private String password;
    private Set<String> roles = new HashSet<>();

    public AppUserDto() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

}
