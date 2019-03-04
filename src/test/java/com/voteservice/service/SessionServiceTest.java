package com.voteservice.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.converter.SessionConverter;
import com.voteservice.data.DataProvider;
import com.voteservice.model.Session;
import com.voteservice.model.TopicVoting;
import com.voteservice.repository.SessionRepository;

@RunWith(MockitoJUnitRunner.class)
public class SessionServiceTest extends DataProvider {

	@Mock
	private SessionRepository sessionRepository;
	
	@Mock
	private SessionConverter sessionConverter;

	@InjectMocks
	private SessionService sessionService;

	
	@Test
	public void shouldReturnTrueWhenIReceiveACorrectTopicVotingToOpenASession() {
		Session expected = new Session();
		TopicVoting topicVoting = buildTopicVoting();
		
		
		when(sessionConverter.converterTopicVotingToSession(refEq(topicVoting), any(LocalDateTime.class), eq(dateOf2019))).thenReturn(expected);
		when(sessionRepository.save(expected)).thenReturn(expected);
		
		assertTrue(sessionService.openSession(topicVoting, dateOf2019));
		
		verify(sessionConverter).converterTopicVotingToSession(refEq(topicVoting), any(LocalDateTime.class), eq(dateOf2019));
		verify(sessionRepository).save(expected);
	}
	
	
}
