package com.voteservice.controller.request;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class TopicVotingRequest {

	private String description;
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime finalVoting;

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

}
