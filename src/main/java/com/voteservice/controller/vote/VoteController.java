package com.voteservice.controller.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.voteservice.controller.topicvoting.request.VoteRequest;
import com.voteservice.controller.vote.adapter.VoteAdapter;
import com.voteservice.exception.ClosedSessionException;

@RestController
@RequestMapping(value = "/v1/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

	private VoteAdapter voteAdapter;

	@Autowired
	public VoteController(VoteAdapter voteAdapter) {
		this.voteAdapter = voteAdapter;
	}
	
	@PostMapping("/topics-voting/{topicVotingId}/vote")
	public ResponseEntity<?> openSession(@PathVariable Long topicVotingId, @RequestBody VoteRequest voteRequest) {
		return ResponseEntity.ok(voteAdapter.vote(topicVotingId, voteRequest));
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<JsonNode> handleException(IllegalArgumentException e) {
    	HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    	ObjectNode jsonNode = new ObjectMapper().createObjectNode();
		jsonNode.put("status", badRequest.value());
		jsonNode.put("message", e.getMessage());
		return ResponseEntity.status(badRequest).body(jsonNode);
	}
	
	@ExceptionHandler(ClosedSessionException.class)
	public ResponseEntity<JsonNode> handleException(ClosedSessionException e) {
    	HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    	ObjectNode jsonNode = new ObjectMapper().createObjectNode();
		jsonNode.put("status", badRequest.value());
		jsonNode.put("message", e.getMessage());
		return ResponseEntity.status(badRequest).body(jsonNode);
	}
	
}
