package com.voteservice.exception;

public class ClosedSessionException extends RuntimeException {

	private static final long serialVersionUID = 7982938830576964059L;

	public ClosedSessionException(String message) {
		super(message);
	}
	
}
