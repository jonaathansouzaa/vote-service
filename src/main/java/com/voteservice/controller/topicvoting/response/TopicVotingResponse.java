package com.voteservice.controller.topicvoting.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopicVotingResponse {

	@ApiModelProperty(notes = "The description that insert in database")
	private String description;

	public TopicVotingResponse() {
	}
	
	public TopicVotingResponse(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
