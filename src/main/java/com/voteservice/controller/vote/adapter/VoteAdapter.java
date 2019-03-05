package com.voteservice.controller.vote.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voteservice.controller.topicvoting.request.VoteRequest;
import com.voteservice.controller.vote.converter.VoteRequestConverter;
import com.voteservice.controller.vote.converter.VoteResponseConverter;
import com.voteservice.controller.vote.response.VoteResponse;
import com.voteservice.dto.VoteDTO;
import com.voteservice.service.VoteService;

@Component
public class VoteAdapter {

	private VoteRequestConverter voteRequestConverter;
	private VoteService voteService;
	private VoteResponseConverter voteResponseConverter;

	@Autowired
	public VoteAdapter(VoteRequestConverter voteRequestConverter, VoteService voteService, VoteResponseConverter voteResponseConverter) {
		this.voteRequestConverter = voteRequestConverter;
		this.voteService = voteService;
		this.voteResponseConverter = voteResponseConverter;
	}
	
	public VoteResponse vote(Long topicVotingId, VoteRequest voteRequest) {
		VoteDTO topicVotingFromRequest = voteRequestConverter.dtoFromRequest(topicVotingId, voteRequest);
		VoteDTO topicVotingOpenSession = voteService.vote(topicVotingFromRequest);
		VoteResponse topicVotingOpenSessionResponse = voteResponseConverter.responseFromDto(topicVotingOpenSession);
		return topicVotingOpenSessionResponse;
	}

}
