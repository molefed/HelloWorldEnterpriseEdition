package ru.molefed.db.repo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.molefed.db.entity.user.AppRole;
import ru.molefed.db.repo.EntityWithNameAndIdRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long>, EntityWithNameAndIdRepository<AppRole> {

}
