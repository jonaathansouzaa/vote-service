package com.voteservice.controller.topicvoting.request;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class SessionRequest {

	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime finalVoting;

	public SessionRequest() {
	}
	
	public SessionRequest(LocalDateTime finalVoting) {
		this.finalVoting = finalVoting;
	}

	public LocalDateTime getFinalVoting() {
		return finalVoting;
	}

	public void setFinalVoting(LocalDateTime finalVoting) {
		this.finalVoting = finalVoting;
	}
	
	
	
}
