package com.voteservice.controller.topicvoting.request;

public class VoteRequest {

	private String document;
	private Boolean vote;

	public VoteRequest() {
	}
	
	public VoteRequest(String document, Boolean vote) {
		this.document = document;
		this.vote = vote;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Boolean getVote() {
		return vote;
	}

	public void setVote(Boolean vote) {
		this.vote = vote;
	}

}
