package com.voteservice.exception;

public class UnableToVoteException extends RuntimeException {

	private static final long serialVersionUID = -6447595330890225209L;

	public UnableToVoteException(String message) {
		super(message);
	}
	
}
