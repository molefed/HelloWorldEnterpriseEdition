package ru.molefed.security.annotate;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasAnyRole('USER')")
public @interface CanManageBooks {
}
