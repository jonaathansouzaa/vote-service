package com.voteservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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
	
	@Test
	public void shouldReturnAListOfSessionThatCanBeClosed() {
		final Session session01 = new Session(new TopicVoting("Session 01"), LocalDateTime.now().minusMinutes(30), LocalDateTime.now().minusMinutes(20), Boolean.TRUE);
		final Session session02 = new Session(new TopicVoting("Session 02"), LocalDateTime.now().minusMinutes(30), LocalDateTime.now().plusMinutes(20), null);
		final Session session03 = new Session(new TopicVoting("Session 03"), LocalDateTime.now().minusMinutes(30), LocalDateTime.now().minusMinutes(10), Boolean.TRUE);
		
		when(sessionRepository.findByProduceMessageFalseOrProduceMessage(null)).thenReturn(Arrays.asList(session01, session02, session03));
		when(sessionRepository.save(session01)).thenReturn(session01);
		when(sessionRepository.save(session03)).thenReturn(session03);
		
		List<String> sessionClosed = sessionService.doHaveAnOpenSessionThatCanBeClosed();
		
		String description01 = session01.getTopicVoting().getDescription();
		String description03 = session03.getTopicVoting().getDescription();
		assertThat(sessionClosed).usingDefaultComparator().containsExactlyInAnyOrder(description01, description03);
		
		
		verify(sessionRepository).findByProduceMessageFalseOrProduceMessage(null);
		verify(sessionRepository).save(session01);
		verify(sessionRepository, never()).save(session02);
		verify(sessionRepository).save(session03);
	}
	
}
