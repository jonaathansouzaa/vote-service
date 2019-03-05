package com.voteservice.controller.vote.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.controller.topicvoting.response.TopicVotingResponse;
import com.voteservice.controller.vote.response.VoteResponse;
import com.voteservice.controller.vote.response.VoteResultResponse;
import com.voteservice.dto.VoteDTO;
import com.voteservice.exception.Messages;

@RunWith(MockitoJUnitRunner.class)
public class VoteResponseConverterTest {

	@InjectMocks
	private VoteResponseConverter voteResponseConverter;

	private final String topicVotingDescription = RandomStringUtils.randomAlphabetic(10);
	
	@Test
	public void shouldReturnAVoteResponseWithMessageThatTheVoteIsOk() {
		final VoteDTO voteDto = new VoteDTO();
		final VoteResponse expected = new VoteResponse(Messages.YOUR_VOTE_IS_OK);
		
		VoteResponse actual = voteResponseConverter.responseFromDto(voteDto);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnAVoteResponseWithMessageThatYouCanNotVote() {
		final VoteDTO voteDto = null;
		final VoteResponse expected = new VoteResponse(Messages.YOU_CAN_NOT_VOTE);
		
		VoteResponse actual = voteResponseConverter.responseFromDto(voteDto);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnAVoteResponseWithYesWon() {
		final Long countYes = 10L;
		final Long countNo = 5L;
		final VoteDTO voteDto = new VoteDTO(topicVotingDescription, countYes, countNo );
		final TopicVotingResponse topicVoting = new TopicVotingResponse(topicVotingDescription);
		final VoteResultResponse voteResult = new VoteResultResponse(countYes, countNo);
		final VoteResponse expected = new VoteResponse(topicVoting, voteResult, Messages.THE_OPTION_YES_WIN);
		
		VoteResponse actual = voteResponseConverter.responseFromDto(voteDto, topicVotingDescription);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnATopicVotingResponseWithNoWon() {
		final Long countYes = 5L;
		final Long countNo = 10L;
		final VoteDTO voteDto = new VoteDTO(topicVotingDescription, countYes, countNo );
		final TopicVotingResponse topicVoting = new TopicVotingResponse(topicVotingDescription);
		final VoteResultResponse voteResult = new VoteResultResponse(countYes, countNo);
		final VoteResponse expected = new VoteResponse(topicVoting, voteResult, Messages.THE_OPTION_NO_WIN);
		
		VoteResponse actual = voteResponseConverter.responseFromDto(voteDto, topicVotingDescription);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnAVoteResponseWithSameResult() {
		final Long countYes = 10L;
		final Long countNo = 10L;
		final VoteDTO voteDto = new VoteDTO(topicVotingDescription, countYes, countNo );
		final TopicVotingResponse topicVoting = new TopicVotingResponse(topicVotingDescription);
		final VoteResultResponse voteResult = new VoteResultResponse(countYes, countNo);
		final VoteResponse expected = new VoteResponse(topicVoting, voteResult, Messages.THE_OPTION_IS_THE_SAME);
		
		VoteResponse actual = voteResponseConverter.responseFromDto(voteDto, topicVotingDescription);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
}
