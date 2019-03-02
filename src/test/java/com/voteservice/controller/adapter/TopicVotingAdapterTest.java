package com.voteservice.controller.adapter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.controller.converter.TopicVotingRequestConverter;
import com.voteservice.controller.converter.TopicVotingResponseConverter;
import com.voteservice.controller.request.TopicVotingRequest;
import com.voteservice.controller.response.TopicVotingResponse;
import com.voteservice.data.DataProvider;
import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.exception.Messages;
import com.voteservice.service.TopicVotingService;

@RunWith(MockitoJUnitRunner.class)
public class TopicVotingAdapterTest extends DataProvider {

	@Mock
	private TopicVotingRequestConverter topicVotingRequestConverter;

	@Mock
	private TopicVotingService topicVotingService;
	
	@Mock
	private TopicVotingResponseConverter topicVotingResponseConverter;

	@InjectMocks
	private TopicVotingAdapter TopicVotingAdapter;

	
	@Test
	public void shouldReturnAResponseWhenARequestIsCorrectly() {
		TopicVotingRequest topicVotingRequest = buildRequest();
		TopicVotingDTO topicVotingDTO = buildTopicVotingDTO();
		TopicVotingResponse expected = buildResponse(topicVotingDTO);
		
		when(topicVotingRequestConverter.dtoFromRequest(topicVotingRequest)).thenReturn(topicVotingDTO);
		when(topicVotingService.save(topicVotingDTO)).thenReturn(topicVotingDTO);
		when(topicVotingResponseConverter.responseFromDto(topicVotingDTO)).thenReturn(expected);
		
		TopicVotingResponse actual = TopicVotingAdapter.handleRequest(topicVotingRequest);
		assertEquals(expected, actual);
		
		verify(topicVotingRequestConverter).dtoFromRequest(topicVotingRequest);
		verify(topicVotingService).save(topicVotingDTO);
		verify(topicVotingResponseConverter).responseFromDto(topicVotingDTO);
	}
	
	@Test
	public void shouldReturnAnExceptionWhenARequestIsWithoutDescription() {
		TopicVotingRequest topicVotingRequest = buildRequestWithoutDescription();
		
		when(topicVotingRequestConverter.dtoFromRequest(topicVotingRequest)).thenThrow(new IllegalArgumentException(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED));
		
		assertThatThrownBy(() -> TopicVotingAdapter.handleRequest(topicVotingRequest))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED);
		
		verify(topicVotingRequestConverter).dtoFromRequest(topicVotingRequest);
	}
	
	@Test
	public void shouldReturnAnExceptionWhenARequestIsWithEmptyDescription() {
		TopicVotingRequest topicVotingRequest = buildRequestWithEmptyDescription();
		
		when(topicVotingRequestConverter.dtoFromRequest(topicVotingRequest)).thenThrow(new IllegalArgumentException(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED));
		
		assertThatThrownBy(() -> TopicVotingAdapter.handleRequest(topicVotingRequest))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED);
		
		verify(topicVotingRequestConverter).dtoFromRequest(topicVotingRequest);
	}

}
