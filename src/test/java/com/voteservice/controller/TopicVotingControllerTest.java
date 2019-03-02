package com.voteservice.controller;

import static org.mockito.ArgumentMatchers.any;
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
import com.voteservice.controller.request.TopicVotingRequest;
import com.voteservice.controller.response.TopicVotingResponse;
import com.voteservice.exception.Messages;

@RunWith(SpringRunner.class)
@WebMvcTest(TopicVotingController.class)
@ActiveProfiles("test")
@Import(ContextsLoads.class)
public class TopicVotingControllerTest extends BaseControllerTest {

	private static final String TOPIC_VOTING_INSERT = "/v1/topic-voting";
	
	private ObjectMapper mapper;
	
	@MockBean
	private TopicVotingAdapter TopicVotingAdapter;
	
	@Autowired
    protected MockMvc mockMvc;
	
	@Before
	public void setUp() {
		this.mapper = new ObjectMapper();
	}
	
	@Test
	public void shouldReturnSuccessWithTheCorrectlyMessageIfTheRequestIsOk() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/topicvoting/topic-voting-request.json");
		final String expectedResponse = loadResourceAsString("json/topicvoting/topic-voting-response.json");
		
		TopicVotingResponse topicVotingResponse = new TopicVotingResponse();
		topicVotingResponse.setDescription("Voting about XYZ");
		topicVotingResponse.setFinalVoting(LocalDateTime.of(2019, 1, 1, 0, 0));
		
		when(TopicVotingAdapter.handleRequest(any(TopicVotingRequest.class))).thenReturn(topicVotingResponse);
		
		mockMvc.perform(post(TOPIC_VOTING_INSERT)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedResponse));
		
		verify(TopicVotingAdapter).handleRequest(any(TopicVotingRequest.class));
	}
	
	@Test
	public void shouldReturnSuccessWithTheCorrectlyMessageIfTheRequestWithNullFinalVotingIsOk() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/topicvoting/topic-voting-request-with-null-final-voting.json");
		final String expectedResponse = loadResourceAsString("json/topicvoting/topic-voting-response-with-null-final-voting.json");
		
		TopicVotingResponse topicVotingResponse = new TopicVotingResponse();
		topicVotingResponse.setDescription("Voting about XYZ");
		topicVotingResponse.setFinalVoting(null);
		
		when(TopicVotingAdapter.handleRequest(any(TopicVotingRequest.class))).thenReturn(topicVotingResponse);
		
		mockMvc.perform(post(TOPIC_VOTING_INSERT)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedResponse));
		
		verify(TopicVotingAdapter).handleRequest(any(TopicVotingRequest.class));
	}
	
	@Test
	public void shouldReturnAnExceptionWithIfTheRequestWithNullDescriptionIsOk() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/topicvoting/error/topic-voting-request-with-null-description.json");
		final String expectedResponse = loadResourceAsString("json/topicvoting/error/topic-voting-response-with-null-description.json");
		
		when(TopicVotingAdapter.handleRequest(any(TopicVotingRequest.class))).thenThrow(new IllegalArgumentException(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED));
		
		mockMvc.perform(post(TOPIC_VOTING_INSERT)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(expectedResponse));
		
		verify(TopicVotingAdapter).handleRequest(any(TopicVotingRequest.class));
	}
	
	@Test
	public void shouldReturnAnExceptionWithIfTheRequestWithEmptyDescriptionIsOk() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/topicvoting/error/topic-voting-request-with-empty-description.json");
		final String expectedResponse = loadResourceAsString("json/topicvoting/error/topic-voting-response-with-empty-description.json");
		
		when(TopicVotingAdapter.handleRequest(any(TopicVotingRequest.class))).thenThrow(new IllegalArgumentException(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED));
		
		mockMvc.perform(post(TOPIC_VOTING_INSERT)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(expectedResponse));
		
		verify(TopicVotingAdapter).handleRequest(any(TopicVotingRequest.class));
	}
	
	@Test
	public void shouldReturnAnExceptionWithRequestWithoutDescriptionIsOk() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/topicvoting/error/topic-voting-request-without-description.json");
		final String expectedResponse = loadResourceAsString("json/topicvoting/error/topic-voting-response-without-description.json");
		
		when(TopicVotingAdapter.handleRequest(any(TopicVotingRequest.class))).thenThrow(new IllegalArgumentException(Messages.THE_FIELD_DESCRIPTION_IS_REQUIRED));
		
		mockMvc.perform(post(TOPIC_VOTING_INSERT)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(expectedResponse));
		
		verify(TopicVotingAdapter).handleRequest(any(TopicVotingRequest.class));
	}
	
}
