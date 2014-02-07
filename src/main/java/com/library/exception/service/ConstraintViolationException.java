package com.library.exception.service;

public class ConstraintViolationException extends Exception{

	private static final long serialVersionUID = 3736162551441791497L;
	
	public ConstraintViolationException(String message) {
		super(message);
	}

	public ConstraintViolationException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
