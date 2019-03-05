package com.voteservice.controller.topicvoting;

import static org.mockito.ArgumentMatchers.any;
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
import com.voteservice.controller.topicvoting.adapter.TopicVotingAdapter;
import com.voteservice.controller.topicvoting.request.TopicVotingRequest;
import com.voteservice.controller.topicvoting.response.TopicVotingResponse;
import com.voteservice.exception.Messages;

@RunWith(SpringRunner.class)
@WebMvcTest(TopicVotingController.class)
@ActiveProfiles("test")
@Import(ContextsLoads.class)
public class TopicVotingControllerTest extends BaseControllerTest {

	private static final String TOPIC_VOTING_INSERT = "/v1/topics-voting";
	
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

}
