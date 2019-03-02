package com.voteservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
	
	@InjectMocks
	private TopicVotingService topicVotingService;
	
	@Test
	public void shouldReturnAVotingScheduleWhenISaveWithSuccess() {
		TopicVotingDTO topicVotingIn = new TopicVotingDTO();
		TopicVotingDTO topicVotingOut = new TopicVotingDTO();
		TopicVoting topicVoting = buildTopicVoting(topicVotingIn, RandomUtils.nextLong());
		
		when(topicVotingConverter.entityFromDTO(topicVotingIn)).thenReturn(topicVoting);
		when(topicVotingRepository.save(topicVoting)).thenReturn(topicVoting);
		when(topicVotingConverter.dtoFromEntiy(topicVoting)).thenReturn(topicVotingOut);
		TopicVotingDTO actual = topicVotingService.save(topicVotingIn);
		
		assertEquals(topicVotingOut, actual);
		
		verify(topicVotingConverter).entityFromDTO(topicVotingIn);
		verify(topicVotingRepository).save(topicVoting);
		verify(topicVotingConverter).dtoFromEntiy(topicVoting);
	}

}
