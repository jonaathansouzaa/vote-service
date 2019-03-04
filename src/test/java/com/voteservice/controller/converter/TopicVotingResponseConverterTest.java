package com.voteservice.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.controller.response.TopicVotingResponse;
import com.voteservice.data.DataProvider;
import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.exception.Messages;

@RunWith(MockitoJUnitRunner.class)
public class TopicVotingResponseConverterTest extends DataProvider {

	@InjectMocks
	private TopicVotingResponseConverter topicVotingResponseConverter;

	@Test
	public void shouldReturnATopicVotingResponseWhenIReceiveATopicVotingDTO() {
		final TopicVotingDTO topicVotingDTO = buildTopicVotingDTO();
		final TopicVotingResponse expected = buildResponse(topicVotingDTO); 
				
		TopicVotingResponse actual = topicVotingResponseConverter.topicVotingResponseFromDto(topicVotingDTO);
		
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	
	@Test
	public void shouldReturnAMessageThatOpenASessionWhenIReturnATopicVotingDTO() {
		final TopicVotingResponse expected = new TopicVotingResponse();
		expected.setMessage(Messages.THE_SESSION_TO_VOTING_HAS_OPENED);
		final TopicVotingDTO topicVotingDTO = buildTopicVotingDTO();
		
		TopicVotingResponse actual = topicVotingResponseConverter.openSessionResponseFromDto(topicVotingDTO);
		
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnATopicVotingResponseWithMessageThatCannotOpenASessionWhenIReturnANullTopicVotingDTO() {
		final TopicVotingResponse expected = new TopicVotingResponse();
		expected.setMessage(Messages.THE_SESSION_TO_VOTING_CAN_NOT_OPEN);
		final TopicVotingDTO topicVotingDTO = null;
		
		TopicVotingResponse actual = topicVotingResponseConverter.openSessionResponseFromDto(topicVotingDTO);

		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
}
