package com.voteservice.controller.request;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class OpenVotingRequest {

	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime finalVoting;

	public OpenVotingRequest() {
	}
	
	public OpenVotingRequest(LocalDateTime finalVoting) {
		this.finalVoting = finalVoting;
	}

	public LocalDateTime getFinalVoting() {
		return finalVoting;
	}

	public void setFinalVoting(LocalDateTime finalVoting) {
		this.finalVoting = finalVoting;
	}
	
	
	
}
