package com.voteservice.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.converter.SessionConverter;
import com.voteservice.converter.TopicVotingConverter;
import com.voteservice.dto.SessionDTO;
import com.voteservice.exception.Messages;
import com.voteservice.model.Session;
import com.voteservice.model.TopicVoting;
import com.voteservice.repository.SessionRepository;

@RunWith(MockitoJUnitRunner.class)
public class SessionServiceTest {

	@Mock
	private SessionRepository sessionRepository;
	
	@Mock
	private SessionConverter sessionConverter;
	
	@Mock
	private TopicVotingConverter topicVotingConverter;

	@Mock
	private TopicVotingService topicVotingService;
	
	@InjectMocks
	private SessionService sessionService;

	@Test
	public void shouldReturnATopicVotingWhenIReceiveATopicVotingDtoValidToOpenSession() {
		final SessionDTO sessionDto = new SessionDTO(RandomUtils.nextLong(), LocalDateTime.now());
		final Optional<TopicVoting> topicVoting = Optional.of(new TopicVoting(RandomStringUtils.randomAlphabetic(10)));
		final Session session = new Session(topicVoting.get(), LocalDateTime.now(), sessionDto.getFinalVoting());
		
		SessionDTO expected = new SessionDTO(Boolean.TRUE);
		
		when(topicVotingService.findById(sessionDto.getTopicVotingId())).thenReturn(topicVoting);
		when(sessionConverter.sessionFromDto(eq(topicVoting.get()), any(LocalDateTime.class), eq(sessionDto))).thenReturn(session);
		when(sessionRepository.save(session)).thenReturn(session);
		when(sessionConverter.dtoFromEntity(session)).thenReturn(expected);
		
		sessionService.openSession(sessionDto);
		
		verify(topicVotingService).findById(sessionDto.getTopicVotingId());
		verify(sessionConverter).sessionFromDto(eq(topicVoting.get()), any(LocalDateTime.class), eq(sessionDto));
		verify(sessionRepository).save(session);
		verify(sessionConverter).dtoFromEntity(session);
	}
	
	@Test
	public void shouldReturnAnExceptionWhenIReceiveAOpenSessionDtoThatTopicVotingNotExists() {
		final SessionDTO sessionDto = new SessionDTO(RandomUtils.nextLong(), LocalDateTime.now());
		
		when(topicVotingService.findById(sessionDto.getTopicVotingId())).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> sessionService.openSession(sessionDto))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(Messages.THE_TOPIC_VOTING_NOT_EXISTS);
		
		verify(topicVotingService).findById(sessionDto.getTopicVotingId());
		verifyZeroInteractions(sessionConverter);
		verifyZeroInteractions(sessionRepository);
	}
	
	@Test
	public void shouldReturnAOpenSessionWhenIReceiveATopicVotingThatIsOpened() {
		TopicVoting topicVoting = new TopicVoting(RandomStringUtils.randomAlphabetic(10));
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startingVoting = now.minusMinutes(10);
		LocalDateTime finalVoting = now.plusMinutes(10);
		
		when(sessionRepository.findByTopicVoting(topicVoting)).thenReturn(Optional.of(new Session(topicVoting, startingVoting, finalVoting)));
		
		assertTrue(sessionService.isSessionOpenOfTopicVoting(topicVoting));
		verify(sessionRepository).findByTopicVoting(topicVoting);
	}
	
	@Test
	public void shouldReturnACloseSessionWhenIReceiveATopicVotingThatIsNotOpen() {
		TopicVoting topicVoting = new TopicVoting(RandomStringUtils.randomAlphabetic(10));
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startingVoting = now.minusMinutes(10);
		LocalDateTime finalVoting = now.minusMinutes(5);
		
		when(sessionRepository.findByTopicVoting(topicVoting)).thenReturn(Optional.of(new Session(topicVoting, startingVoting, finalVoting)));
		
		assertFalse(sessionService.isSessionOpenOfTopicVoting(topicVoting));
		verify(sessionRepository).findByTopicVoting(topicVoting);
	}
	
	@Test
	public void shouldReturnAnExceptionThatSessionNotExistsWhenIReceiveATopicThatDontHaveSession() {
		TopicVoting topicVoting = new TopicVoting(RandomStringUtils.randomAlphabetic(10));
		
		when(sessionRepository.findByTopicVoting(topicVoting)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> sessionService.isSessionOpenOfTopicVoting(topicVoting))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(Messages.THE_SESSION_NOT_EXISTS);
		
		verify(sessionRepository).findByTopicVoting(topicVoting);
	}
	
}
