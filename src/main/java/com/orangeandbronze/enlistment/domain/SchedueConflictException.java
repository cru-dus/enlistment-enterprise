package com.orangeandbronze.enlistment.domain;

public class SchedueConflictException extends RuntimeException {

	public SchedueConflictException() {
	}

	public SchedueConflictException(String message) {
		super(message);
	}

	public SchedueConflictException(Throwable cause) {
		super(cause);
	}

	public SchedueConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public SchedueConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
