package com.dls.exception;

import java.io.Serializable;

/**
 * Custom Exception class inherited from Exception class. Use to send custom
 * exception.
 * 
 * @author Aryan Arora
 *
 */
public class CustomException extends Exception implements Serializable {
	private String message;

	public CustomException() {
		this.message = null;
	}

	public CustomException(String message) {
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public String toString() {
		if (this.message == null) {
			return "CustomException";
		} else {
			return "CustomException" + getMessage();
		}
	}
}
