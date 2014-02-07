package com.library.exception.service;

public class AuthenticationException extends Exception {
	
	private static final long serialVersionUID = 7007353740057243296L;

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
