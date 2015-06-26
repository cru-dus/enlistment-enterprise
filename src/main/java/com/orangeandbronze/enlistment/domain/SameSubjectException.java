package com.orangeandbronze.enlistment.domain;

public class SameSubjectException extends RuntimeException {

	public SameSubjectException() {
	}

	public SameSubjectException(String message) {
		super(message);
	}

	public SameSubjectException(Throwable cause) {
		super(cause);
	}

	public SameSubjectException(String message, Throwable cause) {
		super(message, cause);
	}

	public SameSubjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
