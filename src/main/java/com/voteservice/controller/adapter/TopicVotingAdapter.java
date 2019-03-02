package com.voteservice.controller.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voteservice.controller.converter.TopicVotingRequestConverter;
import com.voteservice.controller.converter.TopicVotingResponseConverter;
import com.voteservice.controller.request.TopicVotingRequest;
import com.voteservice.controller.response.TopicVotingResponse;
import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.service.TopicVotingService;

@Component
public class TopicVotingAdapter {

	private TopicVotingRequestConverter topicVotingRequestConverter;
	private TopicVotingService topicVotingService;
	private TopicVotingResponseConverter topicVotingResponseConverter;

	@Autowired
	public TopicVotingAdapter(TopicVotingRequestConverter topicVotingRequestConverter, TopicVotingService topicVotingService, 
			TopicVotingResponseConverter topicVotingResponseConverter) {
		this.topicVotingRequestConverter = topicVotingRequestConverter;
		this.topicVotingService = topicVotingService;
		this.topicVotingResponseConverter = topicVotingResponseConverter;
	}

	public TopicVotingResponse handleRequest(TopicVotingRequest topicVotingRequest) {
		TopicVotingDTO topicVotingFromRequest = topicVotingRequestConverter.dtoFromRequest(topicVotingRequest);
		TopicVotingDTO topicVotingFromService = topicVotingService.save(topicVotingFromRequest);
		return topicVotingResponseConverter.responseFromDto(topicVotingFromService);
	}

}
