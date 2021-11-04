package ru.molefed.controller.mapper;

import org.mapstruct.Mapper;
import ru.molefed.persister.entity.user.AppRole;

@Mapper
public abstract class AppRoleMapper {

    public String toDTO(AppRole appRole) {
        return appRole.getName();
    }

}
