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
import com.voteservice.controller.request.OpenVotingRequest;
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
	private TopicVotingAdapter topicVotingAdapter;
	
	private final Long topicVotingId = 1L;
	private final OpenVotingRequest openVotingRequest = new OpenVotingRequest(dateOf2019);
	
	@Test
	public void shouldReturnAResponseWhenARequestToInsertTopicVotingIsCorrect() {
		TopicVotingRequest topicVotingRequest = buildRequest();
		TopicVotingDTO topicVotingDTO = buildTopicVotingDTO();
		TopicVotingResponse expected = buildResponse(topicVotingDTO);
		
		when(topicVotingRequestConverter.dtoToInsertFromRequest(topicVotingRequest)).thenReturn(topicVotingDTO);
		when(topicVotingService.save(topicVotingDTO)).thenReturn(topicVotingDTO);
		when(topicVotingResponseConverter.topicVotingResponseFromDto(topicVotingDTO)).thenReturn(expected);
		
		TopicVotingResponse actual = topicVotingAdapter.handleRequest(topicVotingRequest);
		assertEquals(expected, actual);
		
		verify(topicVotingRequestConverter).dtoToInsertFromRequest(topicVotingRequest);
		verify(topicVotingService).save(topicVotingDTO);
		verify(topicVotingResponseConverter).topicVotingResponseFromDto(topicVotingDTO);
	}
	
	@Test
	public void shouldReturnAnExceptionWhenIReceiveARequestWithoutDescription() {
		TopicVotingRequest topicVotingRequest = buildRequestWithoutDescription();
		
		when(topicVotingRequestConverter.dtoToInsertFromRequest(topicVotingRequest)).thenThrow(new IllegalArgumentException(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED));
		
		assertThatThrownBy(() -> topicVotingAdapter.handleRequest(topicVotingRequest))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED);
		
		verify(topicVotingRequestConverter).dtoToInsertFromRequest(topicVotingRequest);
	}
	
	@Test
	public void shouldReturnAResponseWhenARequestToOpenSessionIsCorrect() {
		final TopicVotingDTO topicVotingDTO = buildTopicVotingDTO();
		final TopicVotingResponse expected = new TopicVotingResponse();

		expected.setMessage(Messages.THE_SESSION_TO_VOTING_HAS_OPENED);

		when(topicVotingRequestConverter.openSessionDtoFromRequest(topicVotingId, openVotingRequest)).thenReturn(topicVotingDTO);
		when(topicVotingService.openSession(topicVotingDTO)).thenReturn(topicVotingDTO);
		when(topicVotingResponseConverter.openSessionResponseFromDto(topicVotingDTO)).thenReturn(expected);
		
		TopicVotingResponse actual = topicVotingAdapter.openSession(topicVotingId, openVotingRequest);
		
		assertEquals(expected, actual);
		
		verify(topicVotingRequestConverter).openSessionDtoFromRequest(topicVotingId, openVotingRequest);
		verify(topicVotingService).openSession(topicVotingDTO);
		verify(topicVotingResponseConverter).openSessionResponseFromDto(topicVotingDTO);
	}
	
	@Test
	public void shouldReturnAResponseWhenIReceiveARequestButCannotOpenASession() {
		final TopicVotingDTO topicVotingDTO = null;
		final TopicVotingResponse expected = new TopicVotingResponse();
		expected.setMessage(Messages.THE_SESSION_TO_VOTING_CAN_NOT_OPEN);
		
		when(topicVotingRequestConverter.openSessionDtoFromRequest(topicVotingId, openVotingRequest)).thenReturn(topicVotingDTO);
		when(topicVotingService.openSession(topicVotingDTO)).thenReturn(topicVotingDTO);
		when(topicVotingResponseConverter.openSessionResponseFromDto(topicVotingDTO)).thenReturn(expected);
		
		TopicVotingResponse actual = topicVotingAdapter.openSession(topicVotingId, openVotingRequest);
		
		assertEquals(expected, actual);
		
		verify(topicVotingRequestConverter).openSessionDtoFromRequest(topicVotingId, openVotingRequest);
		verify(topicVotingService).openSession(topicVotingDTO);
		verify(topicVotingResponseConverter).openSessionResponseFromDto(topicVotingDTO);
	}
	
}
