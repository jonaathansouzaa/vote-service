package com.voteservice.converter;

import org.springframework.stereotype.Component;

import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.model.TopicVoting;

@Component
public class TopicVotingConverter {

	public TopicVotingDTO dtoFromEntiy(TopicVoting entity) {
		TopicVotingDTO topicVotingDTO = new TopicVotingDTO();
		topicVotingDTO.setTopicVotingId(entity.getTopicVotingId());
		topicVotingDTO.setDescription(entity.getDescription());
		topicVotingDTO.setFinalVoting(entity.getFinalVoting());
		return topicVotingDTO;
	}

	public TopicVoting entityFromDTO(TopicVotingDTO dto) {
		TopicVoting topicVoting = new TopicVoting();
		topicVoting.setDescription(dto.getDescription());
		topicVoting.setFinalVoting(dto.getFinalVoting());
		return topicVoting;
	}

}
