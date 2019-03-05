package com.voteservice.controller.session.adapter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.controller.session.converter.SessionRequestConverter;
import com.voteservice.controller.session.converter.SessionResponseConverter;
import com.voteservice.controller.session.response.SessionResponse;
import com.voteservice.controller.topicvoting.request.SessionRequest;
import com.voteservice.dto.SessionDTO;
import com.voteservice.exception.Messages;
import com.voteservice.service.SessionService;

@RunWith(MockitoJUnitRunner.class)
public class SessionAdapterTest {

	@Mock
	private SessionRequestConverter sessionRequestConverter;

	@Mock
	private SessionResponseConverter sessionResponseConverter;
	
	@Mock
	private SessionService sesssionService;
	
	@InjectMocks
	private SessionAdapter sessionAdapter;

	@Test
	public void shouldReturnAResponseWhenAOpenVotingRequestToOpenSessionIsCorrect() {
		final SessionRequest sessionRequest = new SessionRequest(LocalDateTime.now());
		final SessionDTO sessionDTO = new SessionDTO(RandomUtils.nextLong(), sessionRequest.getFinalVoting());
		final SessionResponse expected = new SessionResponse(Messages.THE_SESSION_TO_VOTING_HAS_OPENED);

		when(sessionRequestConverter.dtoFromRequest(sessionDTO.getTopicVotingId(), sessionRequest)).thenReturn(sessionDTO);
		when(sesssionService.openSession(sessionDTO)).thenReturn(sessionDTO);
		when(sessionResponseConverter.responseFromDto(sessionDTO)).thenReturn(expected);
		
		SessionResponse actual = sessionAdapter.openSession(sessionDTO.getTopicVotingId(), sessionRequest);
		
		assertEquals(expected, actual);
		
		verify(sessionRequestConverter).dtoFromRequest(sessionDTO.getTopicVotingId(), sessionRequest);
		verify(sesssionService).openSession(sessionDTO);
		verify(sessionResponseConverter).responseFromDto(sessionDTO);
	}
	
}
