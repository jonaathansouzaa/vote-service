package com.voteservice.dto;

public class VoteDTO {

	private Long topicVotingId;
	private String document;
	private Boolean vote;
	private Boolean computedVote;
	private String topicVotingDescription;
	private Long countYes;
	private Long countNo;

	public VoteDTO() {
	}
	
	public VoteDTO(long topicVotingId) {
		this.topicVotingId = topicVotingId;
	}

	public VoteDTO(Boolean computedVote) {
		this.computedVote = computedVote;
	}
	
	public VoteDTO(Long topicVotingId, String document, Boolean vote) {
		this.topicVotingId = topicVotingId;
		this.document = document;
		this.vote = vote;
	}

	public VoteDTO(String topicVotingDescription, Long countYes, Long countNo) {
		this.topicVotingDescription = topicVotingDescription;
		this.countYes = countYes;
		this.countNo = countNo;
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

	public String getTopicVotingDescription() {
		return topicVotingDescription;
	}

	public void setTopicVotingDescription(String topicVotingDescription) {
		this.topicVotingDescription = topicVotingDescription;
	}

	public Long getCountYes() {
		return countYes;
	}

	public void setCountYes(Long countYes) {
		this.countYes = countYes;
	}

	public Long getCountNo() {
		return countNo;
	}

	public void setCountNo(Long countNo) {
		this.countNo = countNo;
	}

}
