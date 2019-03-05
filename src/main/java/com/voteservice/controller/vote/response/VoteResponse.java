package com.voteservice.controller.vote.response;

import com.voteservice.controller.topicvoting.response.TopicVotingResponse;

import io.swagger.annotations.ApiModelProperty;

public class VoteResponse {

	private TopicVotingResponse topicVoting;
	private VoteResultResponse result;
	
	@ApiModelProperty(notes = "The message if the transaction is Ok")
	private String message;

	public VoteResponse() {
	}
	
	public VoteResponse(String message) {
		this.message = message;
	}

	public VoteResponse(TopicVotingResponse topicVoting, VoteResultResponse result, String message) {
		this.topicVoting = topicVoting;
		this.result = result;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public TopicVotingResponse getTopicVoting() {
		return topicVoting;
	}

	public void setTopicVoting(TopicVotingResponse topicVoting) {
		this.topicVoting = topicVoting;
	}

	public VoteResultResponse getResult() {
		return result;
	}

	public void setResult(VoteResultResponse result) {
		this.result = result;
	}

}
