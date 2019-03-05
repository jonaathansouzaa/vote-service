package com.voteservice.controller.vote.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.controller.vote.response.VoteResponse;
import com.voteservice.dto.VoteDTO;
import com.voteservice.exception.Messages;

@RunWith(MockitoJUnitRunner.class)
public class VoteResponseConverterTest {

	@InjectMocks
	private VoteResponseConverter voteResponseConverter;

	@Test
	public void shouldReturnATopicVotingResponseWithMessageThatTheVoteIsOk() {
		final VoteDTO voteDto = new VoteDTO();
		final VoteResponse expected = new VoteResponse(Messages.YOUR_VOTE_IS_OK);
		
		VoteResponse actual = voteResponseConverter.responseFromDto(voteDto);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnATopicVotingResponseWithMessageThatYouCanNotVote() {
		final VoteDTO voteDto = null;
		final VoteResponse expected = new VoteResponse(Messages.YOU_CAN_NOT_VOTE);
		
		VoteResponse actual = voteResponseConverter.responseFromDto(voteDto);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	
}
