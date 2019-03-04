package com.voteservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.converter.TopicVotingConverter;
import com.voteservice.data.DataProvider;
import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.model.TopicVoting;
import com.voteservice.repository.TopicVotingRepository;

@RunWith(MockitoJUnitRunner.class)
public class TopicVotingServiceTest extends DataProvider {

	@Mock 
	private TopicVotingConverter topicVotingConverter;
	
	@Mock
	private TopicVotingRepository topicVotingRepository;
	
	@Mock
	private SessionService sessionService;

	@InjectMocks
	private TopicVotingService topicVotingService;

	
	@Test
	public void shouldReturnATopicVotingWhenISaveWithSuccess() {
		final TopicVotingDTO topicVotingIn = new TopicVotingDTO();
		final TopicVotingDTO expected = new TopicVotingDTO();
		final TopicVoting topicVoting = buildTopicVoting(topicVotingIn, RandomUtils.nextLong());
		
		when(topicVotingConverter.entityFromDTO(topicVotingIn)).thenReturn(topicVoting);
		when(topicVotingRepository.save(topicVoting)).thenReturn(topicVoting);
		when(topicVotingConverter.dtoFromEntiy(topicVoting)).thenReturn(expected);
		
		TopicVotingDTO actual = topicVotingService.save(topicVotingIn);
		
		assertEquals(expected, actual);
		
		verify(topicVotingConverter).entityFromDTO(topicVotingIn);
		verify(topicVotingRepository).save(topicVoting);
		verify(topicVotingConverter).dtoFromEntiy(topicVoting);
	}

	@Test
	public void shouldReturnATopicVotingWhenIReceiveATopicVotingIdValid() {
		final TopicVotingDTO topicVotingDTO = buildTopicVotingDTO(RandomUtils.nextLong());
		final Optional<TopicVoting> topicVoting = Optional.of(buildTopicVoting());
		final TopicVotingDTO expected = buildTopicVotingDTO(topicVoting.get());
		
		when(topicVotingRepository.findById(topicVotingDTO.getTopicVotingId())).thenReturn(topicVoting);
		when(topicVotingConverter.dtoFromEntiy(topicVoting.get())).thenReturn(expected);
		
		TopicVotingDTO actual = topicVotingService.openSession(topicVotingDTO);
		assertEquals(expected, actual);
		
		verify(topicVotingRepository).findById(topicVotingDTO.getTopicVotingId());
		verify(topicVotingConverter).dtoFromEntiy(topicVoting.get());
		verify(sessionService).openSession(topicVoting.get(), topicVotingDTO.getFinalVoting());
	}
	
	@Test
	public void shouldReturnATopicVotingWhenIReceiveATopicVotingIdInValid() {
		final TopicVotingDTO topicVotingDTO = buildTopicVotingDTO(RandomUtils.nextLong());
		final TopicVotingDTO expected = null;
		
		when(topicVotingRepository.findById(topicVotingDTO.getTopicVotingId())).thenReturn(Optional.empty());
		when(topicVotingConverter.dtoFromEntiy(null)).thenReturn(expected);
		
		TopicVotingDTO actual = topicVotingService.openSession(topicVotingDTO);
		assertEquals(expected, actual);
		
		verify(topicVotingRepository).findById(topicVotingDTO.getTopicVotingId());
		verify(topicVotingConverter).dtoFromEntiy(null);
	}
	
}
