package ru.molefed.controller.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ru.molefed.controller.dto.AppUserDto;
import ru.molefed.db.entity.user.AppRole;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.db.repo.user.AppRoleRepository;
import ru.molefed.db.repo.user.AppUserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(uses = {AppRoleMapper.class})
public abstract class AppUserMapper {

    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    AppRoleRepository appRoleRepository;

    @Named("toDto")
    public abstract AppUserDto toDto(AppUser user);

    @IterableMapping(qualifiedByName = "toDto")
    public abstract List<AppUserDto> toDtos(List<AppUser> users);

    public AppUser toDomain(AppUserDto userDto) {
        AppUser user = userDto.getId() == null ?
                new AppUser() : appUserRepository.findById(userDto.getId()).get();
        user.setId(userDto.getId());
        user.setName(userDto.getName());

        // TODO: 30.04.2019 переписать на универсальный мерджинг
        Set<AppRole> rolesDto = new HashSet<>();
        Set<AppRole> roles = new HashSet<>();
        for (String roleName : userDto.getRoles()) {
            AppRole role = appRoleRepository.findByName(roleName);
            if (role == null) {
                throw new IllegalArgumentException("Can't find role " + roleName);
            }
            rolesDto.add(role);

            roles.add(role);
        }
        for (AppRole role : new HashSet<>(user.getRoles())) {
            if (!rolesDto.contains(role)) {
                roles.remove(role);
            }
        }

        user.setRoles(roles);

        return user;
    }

}
