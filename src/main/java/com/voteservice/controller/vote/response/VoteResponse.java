package com.voteservice.controller.vote.response;

import io.swagger.annotations.ApiModelProperty;

public class VoteResponse {

	@ApiModelProperty(notes = "The message if the transaction is Ok")
	private String message;

	public VoteResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
