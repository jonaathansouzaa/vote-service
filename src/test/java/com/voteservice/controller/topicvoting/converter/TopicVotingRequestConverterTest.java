	package com.voteservice.controller.topicvoting.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.controller.topicvoting.request.TopicVotingRequest;
import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.exception.Messages;

@RunWith(MockitoJUnitRunner.class)
public class TopicVotingRequestConverterTest {

	@InjectMocks
	private TopicVotingRequestConverter topicVotingRequestConverter;
	
	@Test
	public void shouldReturnTopicVotingDTOWhenIReceiveACorrectTopicVotingRequest() {
		final TopicVotingRequest topicVotingRequest = new TopicVotingRequest(RandomStringUtils.randomAlphabetic(10));
		final TopicVotingDTO expected = new TopicVotingDTO(null, topicVotingRequest.getDescription());

		TopicVotingDTO actual = topicVotingRequestConverter.dtoFromRequest(topicVotingRequest);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnAnExceptionWhenIReceiveATopicVotingRequestWithoutDescription() {
		final TopicVotingRequest topicVotingRequest = new TopicVotingRequest(null);
		
		assertThatThrownBy(() -> topicVotingRequestConverter.dtoFromRequest(topicVotingRequest))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED);
	}
	
	@Test
	public void shouldReturnAnExceptionWhenIReceiveATopicVotingRequestWithEmptyDescription() {
		final TopicVotingRequest topicVotingRequest = new TopicVotingRequest("");
		
		assertThatThrownBy(() -> topicVotingRequestConverter.dtoFromRequest(topicVotingRequest))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED);
	}
	
}
