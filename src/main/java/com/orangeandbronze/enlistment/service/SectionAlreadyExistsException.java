package com.orangeandbronze.enlistment.service;

public class SectionAlreadyExistsException extends Exception {

	public SectionAlreadyExistsException() {
		
	}

	public SectionAlreadyExistsException(String message) {
		super(message);
		
	}

	public SectionAlreadyExistsException(Throwable cause) {
		super(cause);
		
	}

	public SectionAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public SectionAlreadyExistsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
