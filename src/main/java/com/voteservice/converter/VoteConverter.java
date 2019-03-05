package com.voteservice.converter;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.voteservice.dto.VoteDTO;
import com.voteservice.model.TopicVoting;
import com.voteservice.model.Vote;

@Component
public class VoteConverter {

	public Vote entityFromDto(TopicVoting topicVoting, VoteDTO voteDto) {
		return new Vote(topicVoting, voteDto.getDocument(), voteDto.getVote());
	}

	public VoteDTO dtoFromEntity(Vote entity) {
		return new VoteDTO(Objects.nonNull(entity));
	}

}
