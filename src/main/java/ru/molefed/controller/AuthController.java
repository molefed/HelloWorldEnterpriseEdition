package ru.molefed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.molefed.controller.dto.SignInRequestTO;
import ru.molefed.controller.dto.SignInResponseTO;
import ru.molefed.controller.mapper.AuthMapper;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.security.JwtProvider;
import ru.molefed.service.UserService;

@RestController
@RequestMapping(value = "/auth", method = RequestMethod.POST)
public class AuthController {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthMapper authMapper;

    @Autowired
    public AuthController(UserService userService, JwtProvider jwtProvider, AuthMapper authMapper) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.authMapper = authMapper;
    }

    @Transactional(readOnly = true)
    @RequestMapping("/generateToken")
    public SignInResponseTO signin(@RequestBody SignInRequestTO requestTO) {
        AppUser appUser = userService.get(requestTO.getUsername());
        if (appUser == null ||
                appUser.isDeleted() ||
                !userService.isPasswordValid(appUser, requestTO.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        String token = jwtProvider.generateToken(appUser);
        return authMapper.signin(token);
    }

}
