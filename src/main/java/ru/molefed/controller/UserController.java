package ru.molefed.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.molefed.controller.dto.AppUserDto;
import ru.molefed.controller.dto.SearchAppUserDTO;
import ru.molefed.controller.mapper.AppUserMapper;
import ru.molefed.persister.entity.user.AppUser;
import ru.molefed.security.annotate.CanManageUsers;
import ru.molefed.security.annotate.PermitAuthenticated;
import ru.molefed.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final AppUserMapper appUserMapper;

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

	@PreAuthorize("hasPermission(#id, 'user', 'read')")
	@GetMapping("/get/{id}")
	public AppUserDto get(@PathVariable long id) {
		return appUserMapper.toDto(userService.get(id));
	}

	@PreAuthorize("hasPermission(#principal.name, 'user', 'read')")
	@GetMapping("/current")
	public AppUserDto current(Principal principal) {
		return appUserMapper.toDto(userService.get(principal.getName()));
	}

	@PreAuthorize("hasPermission(#userDto.id, 'user', 'edit')")
	@PostMapping(value = "/save")
	public AppUserDto save(@RequestBody @Valid AppUserDto userDto) {
		AppUser user = userService.save(appUserMapper.toDomain(userDto), userDto.getPassword());
		return appUserMapper.toDto(user);
	}

	@CanManageUsers
	@PostMapping("/delete/{id}")
	public void delete(@PathVariable long id) {
		userService.delete(id);
	}
}
