package com.voteservice.controller.topicvoting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.voteservice.controller.topicvoting.adapter.TopicVotingAdapter;
import com.voteservice.controller.topicvoting.request.TopicVotingRequest;

@RestController
@RequestMapping(value = "/v1/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class TopicVotingController {
	
	private TopicVotingAdapter topicVotingAdapter;

	@Autowired
	public TopicVotingController(TopicVotingAdapter topicVotingAdapter) {
		this.topicVotingAdapter = topicVotingAdapter;
	}
	
	@PostMapping("/topics-voting")
	public ResponseEntity<?> insertTopicVoting(@RequestBody TopicVotingRequest topicVotingRequest) {
		return ResponseEntity.ok(topicVotingAdapter.handleRequest(topicVotingRequest));
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
