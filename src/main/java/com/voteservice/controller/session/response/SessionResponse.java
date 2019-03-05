package com.voteservice.controller.session.response;

public class SessionResponse {

	private String message;

	public SessionResponse() {
	}
	
	public SessionResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
