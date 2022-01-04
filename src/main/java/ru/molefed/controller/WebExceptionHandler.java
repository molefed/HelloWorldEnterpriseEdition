package ru.molefed.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.molefed.controller.mapper.ExceptionMapper;


@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class WebExceptionHandler extends ResponseEntityExceptionHandler {

	private final ExceptionMapper mapper;

	@ExceptionHandler({AuthenticationException.class})
	public ResponseEntity<Object> handleAccessDeniedException(Exception ex) {
		log.error(ex.getMessage(), ex);

		HttpStatus status = HttpStatus.FORBIDDEN;
		if (ex instanceof BadCredentialsException) {
			status = HttpStatus.UNAUTHORIZED;
		}

		return mapper.createResponse(status, ex);
	}

	@ExceptionHandler({Throwable.class})
	public ResponseEntity<Object> handleThrowable(Exception ex) {
		log.error(ex.getMessage(), ex);

		return mapper.createResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}
}
