package com.voteservice.dto;

import java.time.LocalDateTime;

public class TopicVotingDTO {

	private Long topicVotingId;
	private String description;
	private LocalDateTime finalVoting;

	public Long getTopicVotingId() {
		return topicVotingId;
	}

	public void setTopicVotingId(Long topicVotingId) {
		this.topicVotingId = topicVotingId;
	}

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
