package com.voteservice.controller.vote;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voteservice.ContextsLoads;
import com.voteservice.base.controller.BaseControllerTest;
import com.voteservice.controller.topicvoting.request.VoteRequest;
import com.voteservice.controller.vote.VoteController;
import com.voteservice.controller.vote.adapter.VoteAdapter;
import com.voteservice.controller.vote.response.VoteResponse;
import com.voteservice.exception.ClosedSessionException;
import com.voteservice.exception.Messages;

@RunWith(SpringRunner.class)
@WebMvcTest(VoteController.class)
@ActiveProfiles("test")
@Import(ContextsLoads.class)
public class VoteControllerTest extends BaseControllerTest {

	private static final String TOPIC_VOTING_VOTE = "/v1/topics-voting/%s/vote";
	private final Long topicVotingId = 1L;
	
	private ObjectMapper mapper;
	
	@MockBean
	private VoteAdapter voteAdapter;
	
	@Autowired
    protected MockMvc mockMvc;
	
	@Before
	public void setUp() {
		this.mapper = new ObjectMapper();
	}
	
	@Test
	public void shouldReturnSuccessWhenIReceiveACorrectlyRequestToVote() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/vote/vote-request.json");
		final String expectedResponse = loadResourceAsString("json/vote/vote-response.json");
		
		final VoteResponse response = new VoteResponse(Messages.YOUR_VOTE_IS_OK);
		
		when(voteAdapter.vote(eq(topicVotingId), any(VoteRequest.class))).thenReturn(response);
		
		mockMvc.perform(post(String.format(TOPIC_VOTING_VOTE, topicVotingId))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedResponse));
		
		verify(voteAdapter).vote(eq(topicVotingId), any(VoteRequest.class));
	}
	
	@Test
	public void shouldReturnSuccessWhenIReceiveATopicVotingThatNotExits() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/vote/error/topic-voting-not-exist-request.json");
		final String expectedResponse = loadResourceAsString("json/vote/error/topic-voting-not-exist-response.json");
		
		when(voteAdapter.vote(eq(topicVotingId), any(VoteRequest.class))).thenThrow(new IllegalArgumentException(Messages.THE_TOPIC_VOTING_NOT_EXISTS));
		
		mockMvc.perform(post(String.format(TOPIC_VOTING_VOTE, topicVotingId))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(expectedResponse));
		
		verify(voteAdapter).vote(eq(topicVotingId), any(VoteRequest.class));
	}
	
	@Test
	public void shouldReturnSuccessWhenIReceiveASessionClosed() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/vote/error/session-closed-request.json");
		final String expectedResponse = loadResourceAsString("json/vote/error/session-closed-response.json");
		
		when(voteAdapter.vote(eq(topicVotingId), any(VoteRequest.class))).thenThrow(new ClosedSessionException(Messages.THE_SESSION_IS_CLOSED));
		
		mockMvc.perform(post(String.format(TOPIC_VOTING_VOTE, topicVotingId))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(expectedResponse));
		
		verify(voteAdapter).vote(eq(topicVotingId), any(VoteRequest.class));
	}
	
}
