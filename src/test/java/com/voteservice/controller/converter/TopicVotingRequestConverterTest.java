	package com.voteservice.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.controller.request.OpenVotingRequest;
import com.voteservice.controller.request.TopicVotingRequest;
import com.voteservice.data.DataProvider;
import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.exception.Messages;

@RunWith(MockitoJUnitRunner.class)
public class TopicVotingRequestConverterTest extends DataProvider {

	@InjectMocks
	private TopicVotingRequestConverter topicVotingRequestConverter;
	
	@Test
	public void shouldReturnTopicVotingDTOWhenIReceiveACorrectTopicVotingRequest() {
		TopicVotingRequest topicVotingRequest = buildRequest();
		
		TopicVotingDTO expected = buildTopicVotingDTO(topicVotingRequest);
		TopicVotingDTO actual = topicVotingRequestConverter.dtoToInsertFromRequest(topicVotingRequest);
		
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnAnExceptionWhenIReceiveATopicVotingRequestWithoutDescription() {
		TopicVotingRequest topicVotingRequest = buildRequestWithoutDescription();
		
		assertThatThrownBy(() -> topicVotingRequestConverter.dtoToInsertFromRequest(topicVotingRequest))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED);
	}
	
	@Test
	public void shouldReturnAnExceptionWhenIReceiveATopicVotingRequestWithEmptyDescription() {
		TopicVotingRequest topicVotingRequest = buildRequestWithEmptyDescription();
		
		assertThatThrownBy(() -> topicVotingRequestConverter.dtoToInsertFromRequest(topicVotingRequest))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED);
	}
	
	@Test
	public void test1() {
		final Long topicVotingId = RandomUtils.nextLong();
		final OpenVotingRequest openVotingRequest = new OpenVotingRequest(dateOf2019);
		
		TopicVotingDTO expected = buildTopicVotingDTO(topicVotingId, openVotingRequest.getFinalVoting());
		TopicVotingDTO actual = topicVotingRequestConverter.openSessionDtoFromRequest(topicVotingId, openVotingRequest);
		
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}

}
