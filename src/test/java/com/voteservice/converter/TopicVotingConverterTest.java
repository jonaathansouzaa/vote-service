package com.voteservice.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.model.TopicVoting;

@RunWith(MockitoJUnitRunner.class)
public class TopicVotingConverterTest {

	@InjectMocks
	private TopicVotingConverter topicVotingConverter;
	
	@Test
	public void shouldReturnATopicVotingConvertedFromTopicVotingDTO() {
		final TopicVotingDTO topicVotingDto = new TopicVotingDTO(null, RandomStringUtils.randomAlphabetic(10));
		final TopicVoting expected = new TopicVoting(topicVotingDto.getDescription());

		TopicVoting actual = topicVotingConverter.entityFromDTO(topicVotingDto);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnATopicVotingDTOConvertedFromTopicVoting() {
		final TopicVoting topicVoting = new TopicVoting(RandomStringUtils.randomAlphabetic(10));
		final TopicVotingDTO expected = new TopicVotingDTO(topicVoting.getDescription());

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
