package ru.molefed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.controller.dto.AppUserDto;
import ru.molefed.db.entity.user.AppRole;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.db.repo.user.AppRoleRepository;
import ru.molefed.db.repo.user.AppUserRepository;
import ru.molefed.utils.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<AppUser> getAll(Integer page, Integer size) {
        return appUserRepository.findByDeleted(false,
                PageRequest.of(page, size, Sort.Direction.ASC, "id"));
    }

    public AppUser get(long id) {
        return appUserRepository.findById(id).get();
    }

    public AppUser get(String name) {
        return appUserRepository.findByName(name);
    }

    @Transactional
    public AppUser save(AppUser user, String password) {
        if (!StringUtils.isEmpty(password)) {
            user.setEncrytedPassword(passwordEncoder.encode(password));
        }

        return appUserRepository.save(user);
    }

    @Transactional
    public void delete(long id) {
        appUserRepository.deleteById(id);
    }

}
