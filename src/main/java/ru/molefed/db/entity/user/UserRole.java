package ru.molefed.db.entity.user;

import ru.molefed.db.entity.AEntityWithId;

import javax.persistence.*;

//@Entity
//@Table(name = UserRole.TABLE/*,
//        uniqueConstraints = {
//                @UniqueConstraint(name = "UserRoleUserIdRoleIdUk", columnNames = {UserRole.USER_ID, UserRole.ROLE_ID})}*/)
public class UserRole /*extends AEntityWithId*/ {
    public static final String TABLE = "UserRole";
    public static final String USER_ID = "userId";
    public static final String ROLE_ID = "roleId";

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = USER_ID, nullable = false)
//    private AppUser appUser;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = ROLE_ID, nullable = false)
//    private AppRole appRole;
//
//    public UserRole() {
//
//    }
//
//    public AppUser getAppUser() {
//        return appUser;
//    }
//
//    public void setAppUser(AppUser appUser) {
//        this.appUser = appUser;
//    }
//
//    public AppRole getAppRole() {
//        return appRole;
//    }
//
//    public void setAppRole(AppRole appRole) {
//        this.appRole = appRole;
//    }

}
