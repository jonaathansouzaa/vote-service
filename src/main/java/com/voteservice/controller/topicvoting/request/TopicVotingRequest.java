package com.voteservice.controller.topicvoting.request;

public class TopicVotingRequest {

	private String description;
	
	public TopicVotingRequest() {
	}
	
	public TopicVotingRequest(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
