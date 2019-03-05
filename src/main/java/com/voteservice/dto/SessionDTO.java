package com.voteservice.dto;

import java.time.LocalDateTime;

public class SessionDTO {

	private Long topicVotingId;
	private LocalDateTime finalVoting;
	private boolean opened;

	public SessionDTO() {
	}
	
	public SessionDTO(boolean opened) {
		this.opened = opened;
	}

	public SessionDTO(Long topicVotingId, LocalDateTime finalVoting) {
		this.topicVotingId = topicVotingId;
		this.finalVoting = finalVoting;
	}

	public Long getTopicVotingId() {
		return topicVotingId;
	}
	
	public void setTopicVotingId(Long topicVotingId) {
		this.topicVotingId = topicVotingId;
	}

	public LocalDateTime getFinalVoting() {
		return finalVoting;
	}
	
	public void setFinalVoting(LocalDateTime finalVoting) {
		this.finalVoting = finalVoting;
	}

	public boolean isOpened() {
		return opened;
	}
	
	public void setOpened(boolean opened) {
		this.opened = opened;
	}

}
