package com.voteservice.controller.topicvoting.converter;

import org.springframework.stereotype.Component;

import com.voteservice.controller.topicvoting.response.TopicVotingResponse;
import com.voteservice.dto.TopicVotingDTO;

@Component
public class TopicVotingResponseConverter {

	public TopicVotingResponse responseFromDto(TopicVotingDTO topicVotingDto) {
		TopicVotingResponse topicVotingResponse = new TopicVotingResponse();
		topicVotingResponse.setDescription(topicVotingDto.getDescription());
		return topicVotingResponse;
	}

}
