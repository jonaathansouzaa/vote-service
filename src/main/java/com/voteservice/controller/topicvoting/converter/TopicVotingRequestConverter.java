package com.voteservice.controller.topicvoting.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.voteservice.controller.topicvoting.request.TopicVotingRequest;
import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.exception.Messages;

@Component
public class TopicVotingRequestConverter {

	public TopicVotingDTO dtoFromRequest(TopicVotingRequest topicVotingRequest) {
		if (StringUtils.isEmpty(topicVotingRequest.getDescription())) {
			throw new IllegalArgumentException(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED);
		}
		
		TopicVotingDTO topicVotingDTO = new TopicVotingDTO();
		topicVotingDTO.setDescription(topicVotingRequest.getDescription());
		return topicVotingDTO;
	}

}
