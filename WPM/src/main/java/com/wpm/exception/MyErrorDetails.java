package com.wpm.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyErrorDetails {
	private LocalDateTime timestamp;
	private String message;
	private String details;
}
