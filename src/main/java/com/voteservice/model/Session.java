package com.voteservice.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "session")
public class Session {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sessionId;

	@OneToOne()
	@JoinColumn(name = "topic_voting_id")
	private TopicVoting topicVoting;
	
	private LocalDateTime startingVoting;
	
	private LocalDateTime finalVoting;

	public Session() {
	}
	
	public Session(TopicVoting topicVoting, LocalDateTime startingVoting, LocalDateTime finalVoting) {
		this.topicVoting = topicVoting;
		this.startingVoting = startingVoting;
		this.finalVoting = finalVoting;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public TopicVoting getTopicVoting() {
		return topicVoting;
	}

	public void setTopicVoting(TopicVoting topicVoting) {
		this.topicVoting = topicVoting;
	}

	public LocalDateTime getStartingVoting() {
		return startingVoting;
	}

	public void setStartingVoting(LocalDateTime startingVoting) {
		this.startingVoting = startingVoting;
	}

	public LocalDateTime getFinalVoting() {
		return finalVoting;
	}

	public void setFinalVoting(LocalDateTime finalVoting) {
		this.finalVoting = finalVoting;
	}

}
