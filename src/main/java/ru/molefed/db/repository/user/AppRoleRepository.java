package ru.molefed.db.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.molefed.db.entity.user.AppRole;
import ru.molefed.db.repository.EntityWithNameAndIdRepository;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long>, EntityWithNameAndIdRepository<AppRole> {

}
