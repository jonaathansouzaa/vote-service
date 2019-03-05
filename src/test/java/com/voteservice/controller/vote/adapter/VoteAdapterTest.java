package com.voteservice.controller.vote.adapter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.controller.topicvoting.request.VoteRequest;
import com.voteservice.controller.vote.converter.VoteRequestConverter;
import com.voteservice.controller.vote.converter.VoteResponseConverter;
import com.voteservice.controller.vote.response.VoteResponse;
import com.voteservice.dto.VoteDTO;
import com.voteservice.exception.Messages;
import com.voteservice.service.VoteService;

@RunWith(MockitoJUnitRunner.class)
public class VoteAdapterTest {

	@Mock
	private VoteRequestConverter voteRequestConverter;

	@Mock
	private VoteResponseConverter voteResponseConverter;
	
	@Mock
	private VoteService voteService;
	
	@InjectMocks
	private VoteAdapter voteAdapter;
	
	@Test
	public void shouldReturnAResponseWhenARequestToInsertTopicVotingIsCorrect() {
		VoteRequest voteRequest = new VoteRequest(RandomStringUtils.randomAlphabetic(10), RandomUtils.nextBoolean());
		VoteDTO voteDto = new VoteDTO(RandomUtils.nextLong(), voteRequest.getDocument(), voteRequest.getVote());
		
		VoteResponse expected = new VoteResponse(Messages.YOUR_VOTE_IS_OK);
		
		when(voteRequestConverter.dtoFromRequest(voteDto.getTopicVotingId(), voteRequest)).thenReturn(voteDto);
		when(voteService.vote(voteDto)).thenReturn(voteDto);
		when(voteResponseConverter.responseFromDto(voteDto)).thenReturn(expected);
		
		VoteResponse actual = voteAdapter.vote(voteDto.getTopicVotingId(), voteRequest);
		assertEquals(expected, actual);
		
		verify(voteRequestConverter).dtoFromRequest(voteDto.getTopicVotingId(), voteRequest);
		verify(voteService).vote(voteDto);
		verify(voteResponseConverter).responseFromDto(voteDto);
	}
	
}
