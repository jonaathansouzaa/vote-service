package com.voteservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

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
import com.voteservice.controller.adapter.TopicVotingAdapter;
import com.voteservice.controller.request.OpenVotingRequest;
import com.voteservice.controller.request.TopicVotingRequest;
import com.voteservice.controller.response.TopicVotingResponse;
import com.voteservice.exception.Messages;

@RunWith(SpringRunner.class)
@WebMvcTest(TopicVotingController.class)
@ActiveProfiles("test")
@Import(ContextsLoads.class)
public class TopicVotingControllerTest extends BaseControllerTest {

	private static final String TOPIC_VOTING_INSERT = "/v1/topics-voting";
	private static final String TOPIC_VOTING_OPEN_SESSION = "/v1/topics-voting/%s/open-session";
	private final Long topicVotingId = 1L;
	
	private ObjectMapper mapper;
	
	@MockBean
	private TopicVotingAdapter topicVotingAdapter;
	
	@Autowired
    protected MockMvc mockMvc;
	
	@Before
	public void setUp() {
		this.mapper = new ObjectMapper();
	}
	
	@Test
	public void shouldReturnSuccessWhenIReceiveACorrectlyRequestToInsertTopicVoting() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/topicvoting/topic-voting-request.json");
		final String expectedResponse = loadResourceAsString("json/topicvoting/topic-voting-response.json");
		
		TopicVotingResponse topicVotingResponse = new TopicVotingResponse();
		topicVotingResponse.setDescription("Voting about XYZ");
		topicVotingResponse.setFinalVoting(LocalDateTime.of(2019, 1, 1, 0, 0));
		
		when(topicVotingAdapter.handleRequest(any(TopicVotingRequest.class))).thenReturn(topicVotingResponse);
		
		mockMvc.perform(post(TOPIC_VOTING_INSERT)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedResponse));
		
		verify(topicVotingAdapter).handleRequest(any(TopicVotingRequest.class));
	}
	
	@Test
	public void shouldReturnSuccessWhenIReceiveARequestWithNullFinalVotingIsOk() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/topicvoting/topic-voting-request-with-null-final-voting.json");
		final String expectedResponse = loadResourceAsString("json/topicvoting/topic-voting-response-with-null-final-voting.json");
		
		TopicVotingResponse topicVotingResponse = new TopicVotingResponse();
		topicVotingResponse.setDescription("Voting about XYZ");
		topicVotingResponse.setFinalVoting(null);
		
		when(topicVotingAdapter.handleRequest(any(TopicVotingRequest.class))).thenReturn(topicVotingResponse);
		
		mockMvc.perform(post(TOPIC_VOTING_INSERT)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedResponse));
		
		verify(topicVotingAdapter).handleRequest(any(TopicVotingRequest.class));
	}
	
	@Test
	public void shouldReturnAnExceptionWhenIReceiveARequestWithoutDescription() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/topicvoting/error/topic-voting-request-without-description.json");
		final String expectedResponse = loadResourceAsString("json/topicvoting/error/topic-voting-response-without-description.json");
		
		when(topicVotingAdapter.handleRequest(any(TopicVotingRequest.class))).thenThrow(new IllegalArgumentException(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED));
		
		mockMvc.perform(post(TOPIC_VOTING_INSERT)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(expectedResponse));
		
		verify(topicVotingAdapter).handleRequest(any(TopicVotingRequest.class));
	}
	
	@Test
	public void shouldReturnSuccessWhenIReceiveACorrectlyRequestToOpenASession() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/topicvoting/open-session-request.json");
		final String expectedResponse = loadResourceAsString("json/topicvoting/open-session-response.json");
		
		final TopicVotingResponse response = new TopicVotingResponse();
		response.setMessage(Messages.THE_SESSION_TO_VOTING_HAS_OPENED);
		
		when(topicVotingAdapter.openSession(eq(topicVotingId), any(OpenVotingRequest.class))).thenReturn(response);
		
		mockMvc.perform(post(String.format(TOPIC_VOTING_OPEN_SESSION, topicVotingId))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedResponse));
		
		verify(topicVotingAdapter).openSession(eq(topicVotingId), any(OpenVotingRequest.class));
	}
	
	@Test
	public void shouldReturnAnExceptionWhenIReceiveARequestThatICannotOpen() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/topicvoting/error/can-not-open-session-request.json");
		final String expectedResponse = loadResourceAsString("json/topicvoting/error/can-not-open-session-response.json");
		
		when(topicVotingAdapter.openSession(eq(topicVotingId), any(OpenVotingRequest.class))).thenThrow(new IllegalArgumentException(Messages.THE_SESSION_TO_VOTING_CAN_NOT_OPEN));
		
		mockMvc.perform(post(String.format(TOPIC_VOTING_OPEN_SESSION, topicVotingId))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(expectedResponse));
		
		verify(topicVotingAdapter).openSession(eq(topicVotingId), any(OpenVotingRequest.class));
	}
	
}
