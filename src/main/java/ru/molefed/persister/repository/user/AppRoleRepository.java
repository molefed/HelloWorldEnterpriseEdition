package ru.molefed.persister.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.molefed.persister.entity.user.AppRole;
import ru.molefed.persister.repository.EntityWithNameAndIdRepository;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long>, EntityWithNameAndIdRepository<AppRole> {

}
