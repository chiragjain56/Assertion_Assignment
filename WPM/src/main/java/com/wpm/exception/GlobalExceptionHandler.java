package com.wpm.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(PasswordException.class)
	public ResponseEntity<?> handlePassException(PasswordException exp, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), exp.getMessage(), req.getDescription(false));

		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception exp, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), exp.getMessage(), req.getDescription(false));

		return new ResponseEntity<>(err, HttpStatus.METHOD_NOT_ALLOWED);
	}
}
