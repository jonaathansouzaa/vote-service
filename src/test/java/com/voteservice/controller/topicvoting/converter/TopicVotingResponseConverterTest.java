package com.voteservice.controller.topicvoting.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.controller.topicvoting.response.TopicVotingResponse;
import com.voteservice.dto.TopicVotingDTO;

@RunWith(MockitoJUnitRunner.class)
public class TopicVotingResponseConverterTest {

	@InjectMocks
	private TopicVotingResponseConverter topicVotingResponseConverter;

	@Test
	public void shouldReturnATopicVotingResponseWhenIReceiveATopicVotingDTO() {
		final TopicVotingDTO topicVotingDto = new TopicVotingDTO(null, RandomStringUtils.randomAlphabetic(10));
		final TopicVotingResponse expected = new TopicVotingResponse(topicVotingDto.getDescription()); 
				
		TopicVotingResponse actual = topicVotingResponseConverter.responseFromDto(topicVotingDto);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
}
