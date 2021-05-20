package ru.molefed.security.annotate;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Доступно пользователям которые авторизованны в системе.
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("isAuthenticated()")
public @interface PermitAuthenticated {
}
