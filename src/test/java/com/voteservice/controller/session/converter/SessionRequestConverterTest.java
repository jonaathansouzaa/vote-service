	package com.voteservice.controller.session.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.controller.topicvoting.request.SessionRequest;
import com.voteservice.dto.SessionDTO;

@RunWith(MockitoJUnitRunner.class)
public class SessionRequestConverterTest {

	@InjectMocks
	private SessionRequestConverter sessionRequestConverter;
	
	@Test
	public void shouldReturnATopicVotingDtoFromAValidOpenVotingRequest() {
		final Long topicVotingId = RandomUtils.nextLong();
		final SessionRequest sessionRequest = new SessionRequest(LocalDateTime.now());
		
		SessionDTO expected = new SessionDTO(topicVotingId, sessionRequest.getFinalVoting());
		SessionDTO actual = sessionRequestConverter.dtoFromRequest(topicVotingId, sessionRequest);
		
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
}
