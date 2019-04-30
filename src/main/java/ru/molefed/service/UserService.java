package ru.molefed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.db.entity.user.AppRole;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.db.repo.user.AppRoleRepository;
import ru.molefed.db.repo.user.AppUserRepository;
import ru.molefed.dto.AppUserDto;
import ru.molefed.utils.EncrytedPasswordUtils;
import ru.molefed.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppRoleRepository appRoleRepository;

    public List<AppUserDto> getAll(Integer page, Integer size) {

        List<AppUserDto> result = new ArrayList<>();
        List<AppUser> list = appUserRepository.findByDeleted(false, PageRequest.of(page, size, Sort.Direction.ASC, "id"));
        for (AppUser user : list)
            result.add(AppUserDto.toDto(user));

        return result;
    }

    public AppUserDto get(long id) {
        AppUser user = appUserRepository.findById(id).get();
        return AppUserDto.toDto(user);
    }

    public AppUserDto get(String name) {
        AppUser user = appUserRepository.findByName(name);
        if (user == null)
            throw new UsernameNotFoundException("Can't find user " + name);

        return AppUserDto.toDto(user);
    }

    @Transactional
    public AppUserDto save(AppUserDto userDto) {
        AppUser user = userDto.getId() == null ? new AppUser() : appUserRepository.findById(userDto.getId()).get();
        user.setId(userDto.getId());
        user.setName(userDto.getName());

        if (!StringUtils.isEmpty(userDto.getPassword()))
            user.setEncrytedPassword(EncrytedPasswordUtils.encrytePassword(userDto.getPassword()));

        // TODO: 30.04.2019 переписать на универсальный мерджинг
        Set<AppRole> rolesDto = new HashSet<>();
        for (String roleName : userDto.getRoles()) {
            AppRole role = appRoleRepository.findByName(roleName);
            if (role == null)
                throw new IllegalArgumentException("Can't find role " + roleName);
            rolesDto.add(role);

            user.addRole(role);
        }
        for (AppRole role : new HashSet<>(user.getRoles())){
            if (!rolesDto.contains(role)) {
                 user.removeRole(role);
            }
        }

        appUserRepository.save(user);
        return AppUserDto.toDto(user);
    }

    @Transactional
    public void delete(long id) {
        appUserRepository.deleteById(id);
    }
}
