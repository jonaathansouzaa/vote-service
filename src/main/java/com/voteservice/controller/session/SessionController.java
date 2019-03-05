package com.voteservice.controller.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.voteservice.controller.session.adapter.SessionAdapter;
import com.voteservice.controller.session.response.SessionResponse;
import com.voteservice.controller.topicvoting.request.SessionRequest;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/v1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class SessionController {

	private SessionAdapter sessionAdapter;

	@Autowired
	public SessionController(SessionAdapter sessionAdapter) {
		this.sessionAdapter = sessionAdapter;
	}
	
	@RequestMapping(value = "/topics-voting/{topicVotingId}/open-session", method = RequestMethod.POST)
	@ApiOperation(value = "API used to create a new topic voting session", response = SessionResponse.class)
	public ResponseEntity<SessionResponse> openSession(@PathVariable Long topicVotingId, @RequestBody SessionRequest sessionRequest) {
		return ResponseEntity.ok(sessionAdapter.openSession(topicVotingId, sessionRequest));
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<JsonNode> handleException(IllegalArgumentException e) {
    	HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    	ObjectNode jsonNode = new ObjectMapper().createObjectNode();
		jsonNode.put("status", badRequest.value());
		jsonNode.put("message", e.getMessage());
		return ResponseEntity.status(badRequest).body(jsonNode);
	}
	
}
