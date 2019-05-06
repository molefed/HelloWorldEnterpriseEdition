package ru.molefed.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.db.entity.user.AppRole;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.db.repo.user.AppUserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) {
        AppUser appUser = appUserRepository.findByName(userName);

        if (appUser == null)
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");

        List<GrantedAuthority> grantList = new ArrayList<>();
        for (AppRole role : appUser.getRoles()) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
            grantList.add(authority);
        }

        UserDetails userDetails = new User(appUser.getName(),
                appUser.getEncrytedPassword(),
                true,
                true,
                true,
                !appUser.isDeleted(),
                grantList);

        return userDetails;
    }

}