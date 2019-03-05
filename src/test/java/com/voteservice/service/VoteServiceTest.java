package com.voteservice.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.converter.VoteConverter;
import com.voteservice.dto.VoteDTO;
import com.voteservice.exception.ClosedSessionException;
import com.voteservice.exception.Messages;
import com.voteservice.model.TopicVoting;
import com.voteservice.model.Vote;
import com.voteservice.repository.VoteRepository;

@RunWith(MockitoJUnitRunner.class)
public class VoteServiceTest {

	@Mock
	private SessionService sessionService;
	
	@Mock
	private TopicVotingService topicVotingService;
	
	@Mock
	private VoteRepository voteRepository;

	@Mock
	private VoteConverter voteConverter;

	@InjectMocks
	private VoteService voteService;

	
	@Test
	public void shouldReturnAVoteDtoWhenIReceiveAValidVoteDto() {
		final VoteDTO voteDto = new VoteDTO(RandomUtils.nextLong(), RandomStringUtils.randomAlphabetic(11), Boolean.TRUE);
		final Optional<TopicVoting> optionalTopicVoting = Optional.of(new TopicVoting());
		final VoteDTO expected = new VoteDTO(Boolean.TRUE);
		final Vote entity = new Vote();

		when(topicVotingService.findById(voteDto.getTopicVotingId())).thenReturn(optionalTopicVoting);
		when(sessionService.isSessionOpenOfTopicVoting(optionalTopicVoting.get())).thenReturn(Boolean.TRUE);
		when(voteConverter.entityFromDto(optionalTopicVoting.get(), voteDto)).thenReturn(entity);
		when(voteRepository.save(entity)).thenReturn(entity);
		when(voteConverter.dtoFromEntity(entity)).thenReturn(expected);
		
		VoteDTO actual = voteService.vote(voteDto);
		assertEquals(expected, actual);
		
		verify(topicVotingService).findById(voteDto.getTopicVotingId());
		verify(sessionService).isSessionOpenOfTopicVoting(optionalTopicVoting.get());
		verify(voteConverter).entityFromDto(optionalTopicVoting.get(), voteDto);
		verify(voteRepository).save(entity);
		verify(voteConverter).dtoFromEntity(entity);
	}

	@Test
	public void shouldReturnAnExceptionWhenIReceiveATopicVotingThatNotExists() {
		final VoteDTO voteDto = new VoteDTO(RandomUtils.nextLong(), RandomStringUtils.randomAlphabetic(11), Boolean.TRUE);
		final Optional<TopicVoting> optionalTopicVoting = Optional.empty();

		when(topicVotingService.findById(voteDto.getTopicVotingId())).thenReturn(optionalTopicVoting);

		assertThatThrownBy(() -> voteService.vote(voteDto))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(Messages.THE_TOPIC_VOTING_NOT_EXISTS);
		
		verify(topicVotingService).findById(voteDto.getTopicVotingId());
		verifyZeroInteractions(sessionService);
		verifyZeroInteractions(voteConverter);
		verifyZeroInteractions(voteRepository);
		verifyZeroInteractions(voteConverter);
	}

	@Test
	public void shouldReturnAnExceptionWhenIReceiveASessionIsClosed() {
		final VoteDTO voteDto = new VoteDTO(RandomUtils.nextLong(), RandomStringUtils.randomAlphabetic(11), Boolean.TRUE);
		final Optional<TopicVoting> optionalTopicVoting = Optional.of(new TopicVoting());

		when(topicVotingService.findById(voteDto.getTopicVotingId())).thenReturn(optionalTopicVoting);
		when(sessionService.isSessionOpenOfTopicVoting(optionalTopicVoting.get())).thenReturn(Boolean.FALSE);

		assertThatThrownBy(() -> voteService.vote(voteDto))
			.isInstanceOf(ClosedSessionException.class)
			.hasMessage(Messages.THE_SESSION_IS_CLOSED);
		
		verify(topicVotingService).findById(voteDto.getTopicVotingId());
		verify(sessionService).isSessionOpenOfTopicVoting(optionalTopicVoting.get());
		verifyZeroInteractions(voteConverter);
		verifyZeroInteractions(voteRepository);
		verifyZeroInteractions(voteConverter);
	}
	
}
