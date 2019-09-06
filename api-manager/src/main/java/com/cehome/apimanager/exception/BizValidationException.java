package com.cehome.apimanager.exception;

public class BizValidationException extends RuntimeException {
	private static final long serialVersionUID = -2976638970928649290L;

	public BizValidationException() {
		super();
	}

	public BizValidationException(String message) {
		super(message);
	}

	public BizValidationException(Throwable cause) {
		super(cause);
	}

	public BizValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}
