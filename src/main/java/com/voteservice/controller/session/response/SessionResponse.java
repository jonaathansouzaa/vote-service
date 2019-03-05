package com.voteservice.controller.session.response;

import io.swagger.annotations.ApiModelProperty;

public class SessionResponse {

	@ApiModelProperty(notes = "The message if the transaction is Ok")
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
