package com.voteservice.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

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
		final TopicVotingDTO topicVotingDTO = buildTopicVotingDTO();
		final TopicVoting expected = buildTopicVoting(topicVotingDTO, null);

		TopicVoting actual = topicVotingConverter.entityFromDTO(topicVotingDTO);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnATopicVotingDTOConvertedFromTopicVoting() {
		final TopicVoting topicVoting = buildTopicVoting();
		final TopicVotingDTO expected = buildTopicVotingDTO(topicVoting);

		TopicVotingDTO actual = topicVotingConverter.dtoFromEntiy(topicVoting);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnATopicVotingDTOConvertedFromANullTopicVoting() {
		final TopicVoting topicVoting = null;
		final TopicVotingDTO expected = null;

		TopicVotingDTO actual = topicVotingConverter.dtoFromEntiy(topicVoting);
		assertEquals(expected, actual);
	}
	
}
