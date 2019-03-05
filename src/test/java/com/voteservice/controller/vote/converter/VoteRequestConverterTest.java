	package com.voteservice.controller.vote.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.controller.topicvoting.request.VoteRequest;
import com.voteservice.dto.VoteDTO;

@RunWith(MockitoJUnitRunner.class)
public class VoteRequestConverterTest {

	@InjectMocks
	private VoteRequestConverter voteRequestConverter;
	
	@Test
	public void shouldReturnATopicVotingDtoFromAValidVoteRequest() {
		final Long topicVotingId = RandomUtils.nextLong();
		final VoteRequest voteRequest = new VoteRequest(RandomStringUtils.randomAlphabetic(11), RandomUtils.nextBoolean());
		
		VoteDTO expected = new VoteDTO(topicVotingId, voteRequest.getDocument(), voteRequest.getVote());
		VoteDTO actual = voteRequestConverter.dtoFromRequest(topicVotingId, voteRequest);
		
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}

}
