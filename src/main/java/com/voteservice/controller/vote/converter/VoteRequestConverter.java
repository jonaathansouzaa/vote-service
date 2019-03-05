package com.voteservice.controller.vote.converter;

import org.springframework.stereotype.Component;

import com.voteservice.controller.topicvoting.request.VoteRequest;
import com.voteservice.dto.VoteDTO;

@Component
public class VoteRequestConverter {

	public VoteDTO dtoFromRequest(Long topicVotingId, VoteRequest voteRequest) {
		VoteDTO voteDto = new VoteDTO();
		voteDto.setTopicVotingId(topicVotingId);
		voteDto.setDocument(voteRequest.getDocument());
		voteDto.setVote(voteRequest.getVote());
		return voteDto;
	}

}
