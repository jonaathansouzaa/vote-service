package com.voteservice.controller.vote;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.voteservice.controller.topicvoting.response.TopicVotingResponse;
import com.voteservice.controller.vote.adapter.VoteAdapter;
import com.voteservice.controller.vote.response.VoteResponse;
import com.voteservice.controller.vote.response.VoteResultResponse;
import com.voteservice.exception.ClosedSessionException;
import com.voteservice.exception.Messages;
import com.voteservice.exception.NotFoundException;
import com.voteservice.exception.UnableToVoteException;

@RunWith(SpringRunner.class)
@WebMvcTest(VoteController.class)
@ActiveProfiles("test")
@Import(ContextsLoads.class)
public class VoteControllerTest extends BaseControllerTest {

	private static final String TOPIC_VOTING_VOTE = "/v1/topics-voting/%s/vote";
	private static final String TOPIC_VOTING_RESULT = "/v1/topics-voting/%s/result";
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
	public void shouldReturnSuccessWhenIReceiveACorrectlyRequestToResult() throws JsonProcessingException, Exception {
		final String expectedResponse = loadResourceAsString("json/vote/result-vote-response.json");
		
		final TopicVotingResponse topicVotingResponse = new TopicVotingResponse("Vote of president");
		final VoteResultResponse voteResultResponse = new VoteResultResponse(10L, 5L);
		final VoteResponse response = new VoteResponse(topicVotingResponse , voteResultResponse, Messages.THE_OPTION_YES_WIN);
		
		when(voteAdapter.result(topicVotingId)).thenReturn(response);
		
		mockMvc.perform(get(String.format(TOPIC_VOTING_RESULT, topicVotingId))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedResponse));
		
		verify(voteAdapter).result(topicVotingId);
	}
	
	@Test
	public void shouldReturnAnExceptionWhenIReceiveATopicVotingThatNotExits() throws JsonProcessingException, Exception {
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
	public void shouldReturnAnExceptionWhenIReceiveASessionClosed() throws JsonProcessingException, Exception {
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
	
	@Test
	public void shouldReturnAnExceptionWhenIReceiveANotFoundDocument() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/vote/error/not-found-document-request.json");
		final String expectedResponse = loadResourceAsString("json/vote/error/not-found-document-response.json");
		
		when(voteAdapter.vote(eq(topicVotingId), any(VoteRequest.class))).thenThrow(new NotFoundException(Messages.THE_DOCUMENT_NOT_FOUND));
		
		mockMvc.perform(post(String.format(TOPIC_VOTING_VOTE, topicVotingId))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isNotFound())
				.andExpect(content().json(expectedResponse));
		
		verify(voteAdapter).vote(eq(topicVotingId), any(VoteRequest.class));
	}
	
	@Test
	public void shouldReturnAnExceptionWhenIReceiveUnableToVote() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/vote/error/unable-to-vote-request.json");
		final String expectedResponse = loadResourceAsString("json/vote/error/unable-to-vote-response.json");
		
		when(voteAdapter.vote(eq(topicVotingId), any(VoteRequest.class))).thenThrow(new UnableToVoteException(Messages.UNABLE_TO_VOTE));
		
		mockMvc.perform(post(String.format(TOPIC_VOTING_VOTE, topicVotingId))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(expectedResponse));
		
		verify(voteAdapter).vote(eq(topicVotingId), any(VoteRequest.class));
	}
	
}
