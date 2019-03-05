package com.voteservice.controller.topicvoting.adapter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.controller.topicvoting.converter.TopicVotingRequestConverter;
import com.voteservice.controller.topicvoting.converter.TopicVotingResponseConverter;
import com.voteservice.controller.topicvoting.request.TopicVotingRequest;
import com.voteservice.controller.topicvoting.response.TopicVotingResponse;
import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.exception.Messages;
import com.voteservice.service.TopicVotingService;

@RunWith(MockitoJUnitRunner.class)
public class TopicVotingAdapterTest {

	@Mock
	private TopicVotingRequestConverter topicVotingRequestConverter;

	@Mock
	private TopicVotingResponseConverter topicVotingResponseConverter;
	
	@Mock
	private TopicVotingService topicVotingService;
	
	@InjectMocks
	private TopicVotingAdapter topicVotingAdapter;
	
	@Test
	public void shouldReturnAResponseWhenARequestToInsertTopicVotingIsCorrect() {
		final TopicVotingRequest topicVotingRequest = new TopicVotingRequest(RandomStringUtils.randomAlphabetic(10));
		final TopicVotingDTO topicVotingDTO = new TopicVotingDTO(null, topicVotingRequest.getDescription());
		final TopicVotingResponse expected = new TopicVotingResponse(topicVotingRequest.getDescription());
		
		when(topicVotingRequestConverter.dtoFromRequest(topicVotingRequest)).thenReturn(topicVotingDTO);
		when(topicVotingService.save(topicVotingDTO)).thenReturn(topicVotingDTO);
		when(topicVotingResponseConverter.responseFromDto(topicVotingDTO)).thenReturn(expected);
		
		TopicVotingResponse actual = topicVotingAdapter.handleRequest(topicVotingRequest);
		assertEquals(expected, actual);
		
		verify(topicVotingRequestConverter).dtoFromRequest(topicVotingRequest);
		verify(topicVotingService).save(topicVotingDTO);
		verify(topicVotingResponseConverter).responseFromDto(topicVotingDTO);
	}
	
	@Test
	public void shouldReturnAnExceptionWhenIReceiveARequestWithoutDescription() {
		final TopicVotingRequest topicVotingRequest = new TopicVotingRequest(null);
		
		when(topicVotingRequestConverter.dtoFromRequest(topicVotingRequest)).thenThrow(new IllegalArgumentException(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED));
		
		assertThatThrownBy(() -> topicVotingAdapter.handleRequest(topicVotingRequest))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED);
		
		verify(topicVotingRequestConverter).dtoFromRequest(topicVotingRequest);
	}

}
