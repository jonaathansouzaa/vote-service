package com.voteservice.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "topic_voting")
public class TopicVoting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "voting_schedule_id")
	private Long topicVotingId;
	@Column(name = "description")
	private String description;
	@Column(name = "final_voting")
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
