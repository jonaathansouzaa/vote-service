package com.voteservice.controller.session.converter;

import org.springframework.stereotype.Component;

import com.voteservice.controller.session.response.SessionResponse;
import com.voteservice.dto.SessionDTO;
import com.voteservice.exception.Messages;

@Component
public class SessionResponseConverter {

	public SessionResponse responseFromDto(SessionDTO sessionDto) {
		if (sessionDto.isOpened()) {
			return new SessionResponse(Messages.THE_SESSION_TO_VOTING_HAS_OPENED);
		} 
		return new SessionResponse(Messages.THE_SESSION_TO_VOTING_CAN_NOT_OPEN);
	}
	
}
