package ru.molefed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.molefed.Roles;
import ru.molefed.controller.dto.AppUserDto;
import ru.molefed.controller.mapper.AppUserMapper;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AppUserMapper appUserMapper;

    @Secured(Roles.ADMIN)
    @GetMapping(value = "/all", params = {"page", "size"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<AppUserDto> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return appUserMapper.toDtos(userService.getAll(page, size));
    }

    @Secured(Roles.ADMIN)
    @GetMapping("/get/{id}")
    public AppUserDto get(@PathVariable long id) {
        return appUserMapper.toDto(userService.get(id));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/current")
    public AppUserDto current(Principal principal) {
        return appUserMapper.toDto(userService.get(principal.getName()));
    }

    @Secured(Roles.ADMIN)
    @PostMapping(value = "/save")
    public AppUserDto save(@RequestBody AppUserDto userDto) {
        AppUser user = userService.save(appUserMapper.toDomain(userDto), userDto.getPassword());
        return appUserMapper.toDto(user);
    }

    @Secured(Roles.ADMIN)
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }

}
