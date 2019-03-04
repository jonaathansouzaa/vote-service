package com.voteservice.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.voteservice.data.DataProvider;
import com.voteservice.model.Session;
import com.voteservice.model.TopicVoting;

@RunWith(MockitoJUnitRunner.class)
public class SessionConverterTest extends DataProvider {

	@InjectMocks
	private SessionConverter sessionConverter;
	
	private final TopicVoting topicVoting = buildTopicVoting();
	
	@Test
	public void shouldReturnASessionWhenIReceiveATopicVoting() {
		final LocalDateTime finalVoting = LocalDateTime.of(2019, 1, 1, 0, 1);
		Session expected = buildSession(topicVoting, dateOf2019, finalVoting);
		
		Session actual = sessionConverter.converterTopicVotingToSession(topicVoting, dateOf2019, finalVoting);
		
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}
	
	@Test
	public void shouldReturnASessionWhenIReceiveATopicVotingWithouFinalVoting() {
		final LocalDateTime finalVoting = null;
		Session expected = buildSession(topicVoting, dateOf2019, LocalDateTime.of(2019, 1, 1, 0, 1));
		
		Session actual = sessionConverter.converterTopicVotingToSession(topicVoting, dateOf2019, finalVoting);
		
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
	}

}
