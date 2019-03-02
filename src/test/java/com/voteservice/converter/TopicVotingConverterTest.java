package com.voteservice.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.data.DataProvider;
import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.model.TopicVoting;

@RunWith(MockitoJUnitRunner.class)
public class TopicVotingConverterTest extends DataProvider {

	@InjectMocks
	private TopicVotingConverter topicVotingConverter;
	
	@Test
	public void shouldReturnATopicVotingConvertedFromTopicVotingDTO() {
		TopicVotingDTO topicVotingDTO = buildTopicVotingDTO();
		
		TopicVoting expected = buildTopicVoting(topicVotingDTO, null);
		TopicVoting actual = topicVotingConverter.entityFromDTO(topicVotingDTO);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnATopicVotingDTOConvertedFromTopicVoting() {
		TopicVoting topicVoting = buildTopicVoting();
		
		TopicVotingDTO expected = buildTopicVotingDTO(topicVoting);
		TopicVotingDTO actual = topicVotingConverter.dtoFromEntiy(topicVoting);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
}
