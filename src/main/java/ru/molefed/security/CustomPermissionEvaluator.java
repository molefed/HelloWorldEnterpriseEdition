package ru.molefed.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

	@Override
	public boolean hasPermission(
			Authentication auth, Object targetDomainObject, Object permission) {
		return false;
	}

	@Override
	public boolean hasPermission(
			Authentication auth, Serializable targetId, String targetType, Object permission) {
		if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
			return false;
		}

		//TODO: проверить роль на админа

		String permissionStr = (String) permission;
		if ("user".equals(targetType)) {
			if ("read".equals(permissionStr) || "edit".equals(permissionStr)) {
				if (isCurrentUser(auth, targetId)) {
					return true;
				}
			}
		}

		return false;
	}

	@SuppressWarnings("RedundantIfStatement")
	private boolean isCurrentUser(Authentication auth, Serializable targetId) {
		if (targetId instanceof Number) {
			if (((Number) targetId).longValue() == getUserId(auth)) {
				return true;
			}
		} else if (targetId instanceof String) {
			if (targetId.equals(getUserName(auth))) {
				return true;
			}
		}

		return false;
	}

	private long getUserId(Authentication auth) {
		JwtUserDetail detail = (JwtUserDetail) auth.getDetails();
		return detail.getId();
	}

	private String getUserName(Authentication auth) {
		JwtUserDetail detail = (JwtUserDetail) auth.getDetails();
		return detail.getName();
	}
}
