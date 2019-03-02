package com.voteservice.data;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import com.voteservice.controller.request.TopicVotingRequest;
import com.voteservice.controller.response.TopicVotingResponse;
import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.model.TopicVoting;

public class DataProvider {

	private LocalDateTime dateOf2019 = LocalDateTime.of(2019, 1, 1, 0, 0);
	
	protected TopicVotingResponse buildResponse(TopicVotingDTO topicVotingDTO) {
		TopicVotingResponse topicVotingResponse = new TopicVotingResponse();
		topicVotingResponse.setDescription(topicVotingDTO.getDescription());
		topicVotingResponse.setFinalVoting(topicVotingDTO.getFinalVoting());
		return topicVotingResponse;
	}

	protected TopicVotingRequest buildRequest() {
		TopicVotingRequest topicVotingRequest = new TopicVotingRequest();
		topicVotingRequest.setDescription(RandomStringUtils.randomAlphabetic(10));
		topicVotingRequest.setFinalVoting(dateOf2019);
		return topicVotingRequest;
	}
	
	protected TopicVotingRequest buildRequestWithEmptyDescription() {
		TopicVotingRequest topicVotingRequest = buildRequest();
		topicVotingRequest.setDescription("");
		return topicVotingRequest;
	}
	
	protected TopicVotingRequest buildRequestWithoutDescription() {
		TopicVotingRequest topicVotingRequest = buildRequest();
		topicVotingRequest.setDescription(null);
		return topicVotingRequest;
	}
	
	protected TopicVotingDTO buildTopicVotingDTO() {
		TopicVotingDTO topicVotingDTO = new TopicVotingDTO();
		topicVotingDTO.setDescription(RandomStringUtils.randomAlphabetic(10));
		topicVotingDTO.setFinalVoting(dateOf2019);
		return topicVotingDTO;
	}
	
	protected TopicVotingDTO buildTopicVotingDTO(TopicVoting topicVoting) {
		TopicVotingDTO topicVotingDTO = new TopicVotingDTO();
		topicVotingDTO.setTopicVotingId(topicVoting.getTopicVotingId());
		topicVotingDTO.setDescription(topicVoting.getDescription());
		topicVotingDTO.setFinalVoting(topicVoting.getFinalVoting());
		return topicVotingDTO;
	}
	
	protected TopicVotingDTO buildTopicVotingDTO(TopicVotingRequest topicVotingRequest) {
		TopicVotingDTO topicVotingDTO = new TopicVotingDTO();
		topicVotingDTO.setDescription(topicVotingRequest.getDescription());
		topicVotingDTO.setFinalVoting(topicVotingRequest.getFinalVoting());
		return topicVotingDTO;
	}
	
	protected TopicVoting buildTopicVoting(TopicVotingDTO topicVotingDTO, Long votingScheduleId) {
		TopicVoting topicVoting = new TopicVoting();
		topicVoting.setTopicVotingId(votingScheduleId);
		topicVoting.setDescription(topicVotingDTO.getDescription());
		topicVoting.setFinalVoting(topicVotingDTO.getFinalVoting());
		return topicVoting;
	}
	
	protected TopicVoting buildTopicVoting() {
		TopicVoting topicVoting = new TopicVoting();
		topicVoting.setTopicVotingId(RandomUtils.nextLong());
		topicVoting.setDescription(RandomStringUtils.randomAlphabetic(10));
		topicVoting.setFinalVoting(dateOf2019);
		return topicVoting;
	}
	
}
