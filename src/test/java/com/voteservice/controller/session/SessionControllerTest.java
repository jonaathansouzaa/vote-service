package com.voteservice.controller.session;

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
import com.voteservice.controller.session.adapter.SessionAdapter;
import com.voteservice.controller.session.response.SessionResponse;
import com.voteservice.controller.topicvoting.request.SessionRequest;
import com.voteservice.exception.Messages;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
@ActiveProfiles("test")
@Import(ContextsLoads.class)
public class SessionControllerTest extends BaseControllerTest {

	private static final String SESSION_URL = "/v1/topics-voting/%s/open-session";
	private final Long topicVotingId = 1L;
	
	private ObjectMapper mapper;
	
	@MockBean
	private SessionAdapter sessionAdapter;
	
	@Autowired
    protected MockMvc mockMvc;
	
	@Before
	public void setUp() {
		this.mapper = new ObjectMapper();
	}
	
	@Test
	public void shouldReturnSuccessWhenIReceiveACorrectlyRequestToOpenASession() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/session/open-session-request.json");
		final String expectedResponse = loadResourceAsString("json/session/open-session-response.json");
		
		final SessionResponse response = new SessionResponse(Messages.THE_SESSION_TO_VOTING_HAS_OPENED);
		
		when(sessionAdapter.openSession(eq(topicVotingId), any(SessionRequest.class))).thenReturn(response);
		
		mockMvc.perform(post(String.format(SESSION_URL, topicVotingId))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedResponse));
		
		verify(sessionAdapter).openSession(eq(topicVotingId), any(SessionRequest.class));
	}
	
	@Test
	public void shouldReturnAnExceptionWhenIReceiveARequestThatICannotOpen() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/session/error/can-not-open-session-request.json");
		final String expectedResponse = loadResourceAsString("json/session/error/can-not-open-session-response.json");
		
		final SessionResponse response = new SessionResponse(Messages.THE_SESSION_TO_VOTING_CAN_NOT_OPEN);
		
		when(sessionAdapter.openSession(eq(topicVotingId), any(SessionRequest.class))).thenReturn(response);
		
		mockMvc.perform(post(String.format(SESSION_URL, topicVotingId))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedResponse));
		
		verify(sessionAdapter).openSession(eq(topicVotingId), any(SessionRequest.class));
	}
	
	@Test
	public void shouldReturnAnExceptionWhenIReceiveARequestThatNotExists() throws JsonProcessingException, Exception {
		final JsonNode requestJson = loadAsJsonFromResource("json/session/error/topic-voting-not-exist-request.json");
		final String expectedResponse = loadResourceAsString("json/session/error/topic-voting-not-exist-response.json");
		
		when(sessionAdapter.openSession(eq(topicVotingId), any(SessionRequest.class))).thenThrow(new IllegalArgumentException(Messages.THE_TOPIC_VOTING_NOT_EXISTS));
		
		mockMvc.perform(post(String.format(SESSION_URL, topicVotingId))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestJson)))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(expectedResponse));
		
		verify(sessionAdapter).openSession(eq(topicVotingId), any(SessionRequest.class));
	}
	
}
