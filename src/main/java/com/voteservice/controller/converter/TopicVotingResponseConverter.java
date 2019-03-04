package com.voteservice.controller.converter;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.voteservice.controller.response.TopicVotingResponse;
import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.exception.Messages;

@Component
public class TopicVotingResponseConverter {

	public TopicVotingResponse topicVotingResponseFromDto(TopicVotingDTO topicVotingDTO) {
		TopicVotingResponse topicVotingResponse = new TopicVotingResponse();
		topicVotingResponse.setDescription(topicVotingDTO.getDescription());
		topicVotingResponse.setFinalVoting(topicVotingDTO.getFinalVoting());
		return topicVotingResponse;
	}

	public TopicVotingResponse openSessionResponseFromDto(TopicVotingDTO topicVotingDTO) {
		TopicVotingResponse topicVotingResponse = new TopicVotingResponse();
		
		if (Objects.isNull(topicVotingDTO)) {
			topicVotingResponse.setMessage(Messages.THE_SESSION_TO_VOTING_CAN_NOT_OPEN);
		} else {
			topicVotingResponse.setMessage(Messages.THE_SESSION_TO_VOTING_HAS_OPENED);
		}
		
		return topicVotingResponse;
	}

}
