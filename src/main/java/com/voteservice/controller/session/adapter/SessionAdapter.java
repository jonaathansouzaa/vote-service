package com.voteservice.controller.session.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voteservice.controller.session.converter.SessionRequestConverter;
import com.voteservice.controller.session.converter.SessionResponseConverter;
import com.voteservice.controller.session.response.SessionResponse;
import com.voteservice.controller.topicvoting.request.SessionRequest;
import com.voteservice.dto.SessionDTO;
import com.voteservice.service.SessionService;

@Component
public class SessionAdapter {

	private SessionRequestConverter sessionRequestConverter;
	private SessionService sessionService;
	private SessionResponseConverter sessionResponseConverter;

	@Autowired
	public SessionAdapter(SessionRequestConverter sessionRequestConverter, SessionService sessionService, 
			SessionResponseConverter sessionResponseConverter) {
		this.sessionRequestConverter = sessionRequestConverter;
		this.sessionService = sessionService;
		this.sessionResponseConverter = sessionResponseConverter;
	}

	public SessionResponse openSession(Long topicVotingId, SessionRequest sessionRequest) {
		SessionDTO sessionDto = sessionRequestConverter.dtoFromRequest(topicVotingId, sessionRequest);
		SessionDTO sessionDtoFromService = sessionService.openSession(sessionDto);
		SessionResponse sessionResponse = sessionResponseConverter.responseFromDto(sessionDtoFromService);
		return sessionResponse;
	}

}
