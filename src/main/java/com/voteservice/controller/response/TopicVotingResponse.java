package com.voteservice.controller.response;

import java.time.LocalDateTime;

public class TopicVotingResponse {

	private String description;
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
