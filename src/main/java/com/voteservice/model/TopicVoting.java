package com.voteservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "topic_voting")
public class TopicVoting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "topic_voting_id")
	private Long topicVotingId;
	
	@Column(name = "description")
	private String description;
	
	@OneToOne(mappedBy = "topicVoting")
	private Session session;
	
	public TopicVoting() {
	}

	public TopicVoting(String description) {
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
