package com.voteservice.dto;

public class VoteDTO {

	private Long topicVotingId;
	private String document;
	private Boolean vote;
	private Boolean computedVote;

	public VoteDTO() {
	}

	public VoteDTO(Boolean computedVote) {
		this.computedVote = computedVote;
	}
	
	public VoteDTO(Long topicVotingId, String document, Boolean vote) {
		this.topicVotingId = topicVotingId;
		this.document = document;
		this.vote = vote;
	}

	public Long getTopicVotingId() {
		return topicVotingId;
	}

	public void setTopicVotingId(Long topicVotingId) {
		this.topicVotingId = topicVotingId;
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

	public Boolean getComputedVote() {
		return computedVote;
	}

	public void setComputedVote(Boolean computedVote) {
		this.computedVote = computedVote;
	}

}
