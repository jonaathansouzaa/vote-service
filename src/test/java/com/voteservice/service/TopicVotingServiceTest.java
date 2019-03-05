package com.voteservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.converter.TopicVotingConverter;
import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.model.TopicVoting;
import com.voteservice.repository.TopicVotingRepository;

@RunWith(MockitoJUnitRunner.class)
public class TopicVotingServiceTest {

	@Mock 
	private TopicVotingConverter topicVotingConverter;
	
	@Mock
	private TopicVotingRepository topicVotingRepository;
	
	@Mock
	private SessionService sessionService;

	@Mock
	private VoteService voteService;

	@InjectMocks
	private TopicVotingService topicVotingService;
	
	@Test
	public void shouldReturnATopicVotingWhenISaveWithSuccess() {
		final TopicVotingDTO topicVotingIn = new TopicVotingDTO(RandomStringUtils.randomAlphabetic(10));
		final TopicVotingDTO expected = new TopicVotingDTO(topicVotingIn.getDescription());
		final TopicVoting topicVoting = new TopicVoting(topicVotingIn.getDescription());
		
		when(topicVotingConverter.entityFromDTO(topicVotingIn)).thenReturn(topicVoting);
		when(topicVotingRepository.save(topicVoting)).thenReturn(topicVoting);
		when(topicVotingConverter.dtoFromEntiy(topicVoting)).thenReturn(expected);
		
		TopicVotingDTO actual = topicVotingService.save(topicVotingIn);
		
		assertEquals(expected, actual);
		
		verify(topicVotingConverter).entityFromDTO(topicVotingIn);
		verify(topicVotingRepository).save(topicVoting);
		verify(topicVotingConverter).dtoFromEntiy(topicVoting);
		verifyZeroInteractions(sessionService);
	}
	
	@Test
	public void shouldReturnATopicVotingWhenIsFound() {
		Long topicVotingId = RandomUtils.nextLong();
		Optional<TopicVoting> expected = Optional.of(new TopicVoting());

		when(topicVotingRepository.findById(topicVotingId)).thenReturn(expected);
		
		Optional<TopicVoting> actual = topicVotingService.findById(topicVotingId);
		assertEquals(expected, actual);
	}
	
}
