package com.voteservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vote")
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long voteId;

	@OneToOne()
	@JoinColumn(name = "topic_voting_id")
	private TopicVoting topicVoting;

	private String document;

	private Boolean vote;

	public Vote() {
	}
	
	public Vote(TopicVoting topicVoting, String document, Boolean vote) {
		this.topicVoting = topicVoting;
		this.document = document;
		this.vote = vote;
	}

	public Long getVoteId() {
		return voteId;
	}

	public void setVoteId(Long voteId) {
		this.voteId = voteId;
	}

	public TopicVoting getTopicVoting() {
		return topicVoting;
	}

	public void setTopicVoting(TopicVoting topicVoting) {
		this.topicVoting = topicVoting;
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
