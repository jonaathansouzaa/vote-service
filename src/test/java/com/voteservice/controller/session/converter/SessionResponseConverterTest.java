package com.voteservice.controller.session.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.controller.session.response.SessionResponse;
import com.voteservice.dto.SessionDTO;
import com.voteservice.exception.Messages;

@RunWith(MockitoJUnitRunner.class)
public class SessionResponseConverterTest {

	@InjectMocks
	private SessionResponseConverter sessionResponseConverter;

	@Test
	public void shouldReturnATopicVotingResponseWhenIReceiveATopicVotingDTO() {
		final SessionDTO sessionDto = new SessionDTO(Boolean.TRUE);
		final SessionResponse expected = new SessionResponse(Messages.THE_SESSION_TO_VOTING_HAS_OPENED);
		SessionResponse actual = sessionResponseConverter.responseFromDto(sessionDto);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnAMessageThatOpenASessionWhenIReturnATopicVotingDTO() {
		final SessionDTO sessionDto = new SessionDTO(Boolean.FALSE);
		final SessionResponse expected = new SessionResponse(Messages.THE_SESSION_TO_VOTING_CAN_NOT_OPEN);
		
		SessionResponse actual = sessionResponseConverter.responseFromDto(sessionDto);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
}
