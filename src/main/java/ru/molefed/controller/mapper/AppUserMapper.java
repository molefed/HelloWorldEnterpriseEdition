package ru.molefed.controller.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ru.molefed.controller.dto.AppUserDto;
import ru.molefed.persister.entity.AEntityWithNameAndId;
import ru.molefed.persister.entity.user.AppRole;
import ru.molefed.persister.entity.user.AppUser;
import ru.molefed.persister.repository.user.AppRoleRepository;
import ru.molefed.persister.repository.user.AppUserRepository;
import ru.molefed.utils.CollectionMerger;

import java.util.List;

@Mapper(uses = {AppRoleMapper.class})
public abstract class AppUserMapper {

	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private AppRoleRepository appRoleRepository;

	@Named("toLiteUser")
	public abstract AEntityWithNameAndId toLiteUser(AppUser user);

	@Named("toDto")
	public abstract AppUserDto toDto(AppUser user);

	@IterableMapping(qualifiedByName = "toDto")
	public abstract List<AppUserDto> toDtos(List<AppUser> users);

	public AppUser toDomain(AppUserDto userDto) {
		AppUser user = userDto.getId() == null ?
				new AppUser() : appUserRepository.findById(userDto.getId()).orElseThrow();
		user.setId(userDto.getId());
		user.setName(userDto.getName());

		new CollectionMerger<String, AppRole>() {
			@Override
			protected AppRole create(String roleName) {
				AppRole role = appRoleRepository.findByName(roleName);
				if (role == null) {
					throw new IllegalArgumentException("Can't find role " + roleName);
				}
				return role;
			}

			@Override
			protected boolean equals(String roleName, AppRole appRole) {
				return appRole.getName().equals(roleName);
			}
		}.merge(userDto.getRoles(), user.getRoles());

		return user;
	}
}
