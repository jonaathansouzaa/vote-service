package com.voteservice.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.dto.VoteDTO;
import com.voteservice.model.TopicVoting;
import com.voteservice.model.Vote;

@RunWith(MockitoJUnitRunner.class)
public class VoteConverterTest {

	@InjectMocks
	private VoteConverter voteConverter;
	
	@Test
	public void shouldReturnAVoteWhenIReceiveAVoteInformations() {
		final TopicVoting topicVoting = new TopicVoting(RandomStringUtils.randomAlphabetic(10));
		final VoteDTO voteDto = new VoteDTO(RandomUtils.nextLong(), RandomStringUtils.randomAlphabetic(10), RandomUtils.nextBoolean());
		final Vote expected = new Vote(topicVoting, voteDto.getDocument(), voteDto.getVote());
		
		Vote actual = voteConverter.entityFromDto(topicVoting, voteDto);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnAVoteDtoWhenIReceiveAVote() {
		final TopicVoting topicVoting = new TopicVoting(RandomStringUtils.randomAlphabetic(10));
		final Vote entity = new Vote(topicVoting, RandomStringUtils.randomAlphabetic(10), RandomUtils.nextBoolean());
		VoteDTO expected = new VoteDTO(Boolean.TRUE);
		VoteDTO actual = voteConverter.dtoFromEntity(entity);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnAVoteDtoWhenIReceiveANullVote() {
		final Vote entity = null;
		VoteDTO expected = new VoteDTO(Boolean.FALSE);
		VoteDTO actual = voteConverter.dtoFromEntity(entity);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnAVoteDtoWhenIReceiveAResult() {
		String description = RandomStringUtils.randomAlphabetic(10);
		final TopicVoting entity = new TopicVoting(description);
		VoteDTO expected = new VoteDTO(description, RandomUtils.nextLong(), RandomUtils.nextLong());
		VoteDTO actual = voteConverter.dtoFromEntity(entity, expected.getCountYes(), expected.getCountNo());
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
}
