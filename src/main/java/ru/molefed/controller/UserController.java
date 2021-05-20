package ru.molefed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.molefed.controller.dto.AppUserDto;
import ru.molefed.controller.dto.SearchAppUserDTO;
import ru.molefed.controller.mapper.AppUserMapper;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.security.annotate.CanManageUsers;
import ru.molefed.security.annotate.PermitAuthenticated;
import ru.molefed.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AppUserMapper appUserMapper;

    @Autowired
    public UserController(UserService userService, AppUserMapper appUserMapper) {
        this.userService = userService;
        this.appUserMapper = appUserMapper;
    }

    @CanManageUsers
    @GetMapping(value = "/all", params = {"page", "size"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<AppUserDto> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return appUserMapper.toDtos(userService.getAll(page, size));
    }

    @CanManageUsers
    @PostMapping(value = "/search")
    public List<AppUserDto> search(@RequestBody SearchAppUserDTO searchAppUserDTO) {
        return appUserMapper.toDtos(userService.search(searchAppUserDTO.getPattern()));
    }

    @CanManageUsers
    @GetMapping("/get/{id}")
    public AppUserDto get(@PathVariable long id) {
        return appUserMapper.toDto(userService.get(id));
    }

    @PermitAuthenticated
    @GetMapping("/current")
    public AppUserDto current(Principal principal) {
        return appUserMapper.toDto(userService.get(principal.getName()));
    }

    @CanManageUsers
    @PostMapping(value = "/save")
    public AppUserDto save(@RequestBody AppUserDto userDto) {
        AppUser user = userService.save(appUserMapper.toDomain(userDto), userDto.getPassword());
        return appUserMapper.toDto(user);
    }

    @CanManageUsers
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }

}
