package com.orangeandbronze.enlistment.domain;

public class PrereqNotTakenException extends RuntimeException {

	public PrereqNotTakenException() {
	}

	public PrereqNotTakenException(String message) {
		super(message);
	}

	public PrereqNotTakenException(Throwable cause) {
		super(cause);
	}

	public PrereqNotTakenException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrereqNotTakenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
