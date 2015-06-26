package com.orangeandbronze.enlistment.domain;

public class SemesterClosedException extends RuntimeException {

	public SemesterClosedException() {
	}

	public SemesterClosedException(String message) {
		super(message);
	}

	public SemesterClosedException(Throwable cause) {
		super(cause);
	}

	public SemesterClosedException(String message, Throwable cause) {
		super(message, cause);
	}

	public SemesterClosedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
