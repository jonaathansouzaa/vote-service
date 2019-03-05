package com.voteservice.controller.session.converter;

import org.springframework.stereotype.Component;

import com.voteservice.controller.topicvoting.request.SessionRequest;
import com.voteservice.dto.SessionDTO;

@Component
public class SessionRequestConverter {

	public SessionDTO dtoFromRequest(Long topicVotingId, SessionRequest sessionRequest) {
		SessionDTO sessionDTO = new SessionDTO();
		sessionDTO.setTopicVotingId(topicVotingId);
		sessionDTO.setFinalVoting(sessionRequest.getFinalVoting());
		return sessionDTO;
	}

}
