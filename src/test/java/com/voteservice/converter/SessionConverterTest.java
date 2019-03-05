package com.voteservice.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.dto.SessionDTO;
import com.voteservice.model.Session;
import com.voteservice.model.TopicVoting;

@RunWith(MockitoJUnitRunner.class)
public class SessionConverterTest {

	@InjectMocks
	private SessionConverter sessionConverter;
	
	@Test
	public void shouldReturnASessionWhenIReceiveATopicVoting() {
		final TopicVoting topicVoting = new TopicVoting();
		final LocalDateTime startingDate = LocalDateTime.now();
		final LocalDateTime finalVoting = LocalDateTime.of(2019, 1, 1, 0, 1);
		final SessionDTO sessionDto = new SessionDTO(RandomUtils.nextLong(), finalVoting);
		final Session expected = new Session(topicVoting, startingDate, finalVoting);
		
		Session actual = sessionConverter.sessionFromDto(topicVoting, startingDate, sessionDto);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnASessionWhenIReceiveATopicVotingWithouFinalVoting() {
		final TopicVoting topicVoting = new TopicVoting();
		final LocalDateTime startingDate = LocalDateTime.now();
		final SessionDTO sessionDto = new SessionDTO(RandomUtils.nextLong(), null);
		final Session expected = new Session(topicVoting, startingDate, startingDate.plusMinutes(1));
		
		Session actual = sessionConverter.sessionFromDto(topicVoting, startingDate, sessionDto);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnASessionDtoWhenIReceiveAEntity() {
		final TopicVoting topicVoting = new TopicVoting();
		LocalDateTime startingVoting = LocalDateTime.now();
		LocalDateTime finalVoting = LocalDateTime.now();
		final Session session = new Session(topicVoting, startingVoting, finalVoting);
		SessionDTO expected = new SessionDTO(Boolean.TRUE);
		
		SessionDTO actual = sessionConverter.dtoFromEntity(session);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnASessionDtoWhenIReceiveANullEntity() {
		final Session session = null;
		SessionDTO expected = new SessionDTO(Boolean.FALSE);
		
		SessionDTO actual = sessionConverter.dtoFromEntity(session);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}

}
