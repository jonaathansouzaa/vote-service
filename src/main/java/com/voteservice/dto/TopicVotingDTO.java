package com.voteservice.dto;

public class TopicVotingDTO {

	private Long topicVotingId;
	private String description;

	public TopicVotingDTO() {
	}
	
	public TopicVotingDTO(String description) {
		this.description = description;
	}
	
	public TopicVotingDTO(Long topicVotingId, String description) {
		this.topicVotingId = topicVotingId;
		this.description = description;
	}

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

}
