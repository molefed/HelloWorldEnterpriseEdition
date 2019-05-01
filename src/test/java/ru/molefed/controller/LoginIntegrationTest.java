package ru.molefed.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.molefed.BookApplication;
import ru.molefed.Roles;
import ru.molefed.db.entity.user.AppRole;
import ru.molefed.db.repo.user.AppRoleRepository;
import ru.molefed.db.repo.user.AppUserRepository;
import ru.molefed.dto.AppUserDto;
import ru.molefed.service.UserService;
import ru.molefed.utils.JsonUtils;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@ContextConfiguration
public class LoginIntegrationTest {

    @Autowired
    private AppRoleRepository appRoleRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private MockMvc mockMvc;
    private AppUserDto userDto;

    @Before
    public void tearUp() {
        appRoleRepository.save(new AppRole(Roles.USER));

        userDto = new AppUserDto();
        userDto.setName("user1");
        userDto.setPassword("pas1");
        userDto.addRole(Roles.USER);

        userService.save(userDto);
    }

    @After
    public void tearDown() {
        appUserRepository.deleteAll();
        appRoleRepository.deleteAll();
    }

    @Test
    public void login() throws Exception {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDto.getName(),
                userDto.getPassword());
        Authentication authentication = authenticationProvider.authenticate(auth);

        assertEquals(authentication.getName(), userDto.getName());
    }

    @Test
    public void current() throws Exception {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDto.getName(),
                userDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        MvcResult result = mockMvc.perform(get("/users/current").contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        AppUserDto userDto2 = JsonUtils.asObject(result.getResponse().getContentAsString(), AppUserDto.class);
        assertEquals(userDto2.getName(), userDto.getName());
    }

    @Test(expected = BadCredentialsException.class)
    public void badLogin() {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("user", "pas");
        authenticationProvider.authenticate(auth);
    }

    @Test(expected = BadCredentialsException.class)
    public void badPas() {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDto.getName(),
                userDto.getPassword() + "123");
        Authentication authentication = authenticationProvider.authenticate(auth);
    }



}
