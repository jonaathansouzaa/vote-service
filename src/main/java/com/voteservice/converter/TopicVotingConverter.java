package com.voteservice.converter;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.model.TopicVoting;

@Component
public class TopicVotingConverter {

	public TopicVotingDTO dtoFromEntiy(TopicVoting entity) {
		if (Objects.isNull(entity)) {
			return null;
		}
		TopicVotingDTO topicVotingDTO = new TopicVotingDTO();
		topicVotingDTO.setTopicVotingId(entity.getTopicVotingId());
		topicVotingDTO.setDescription(entity.getDescription());
		return topicVotingDTO;
	}

	public TopicVoting entityFromDTO(TopicVotingDTO dto) {
		TopicVoting topicVoting = new TopicVoting();
		topicVoting.setDescription(dto.getDescription());
		return topicVoting;
	}

}
