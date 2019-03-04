package com.voteservice.controller.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopicVotingResponse {

	private String description;
	private LocalDateTime finalVoting;
	private String message;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getFinalVoting() {
		return finalVoting;
	}

	public void setFinalVoting(LocalDateTime finalVoting) {
		this.finalVoting = finalVoting;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
