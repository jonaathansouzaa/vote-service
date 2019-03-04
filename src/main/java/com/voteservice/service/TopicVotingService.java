package com.voteservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voteservice.converter.TopicVotingConverter;
import com.voteservice.dto.TopicVotingDTO;
import com.voteservice.model.TopicVoting;
import com.voteservice.repository.TopicVotingRepository;

@Service
public class TopicVotingService {

	private TopicVotingRepository topicVotingRepository;
	private TopicVotingConverter topicVotingConverter;
	private SessionService sessionService;

	@Autowired
	public TopicVotingService(TopicVotingRepository topicVotingRepository, 
			TopicVotingConverter topicVotingConverter, SessionService sessionService) {
		this.topicVotingRepository = topicVotingRepository;
		this.topicVotingConverter = topicVotingConverter;
		this.sessionService = sessionService;
	}
	
	public TopicVotingDTO save(TopicVotingDTO topicVotingDTO) {
		TopicVoting topicVotingToInsert = topicVotingConverter.entityFromDTO(topicVotingDTO);
		TopicVoting topicVotingInserted = topicVotingRepository.save(topicVotingToInsert);
		return topicVotingConverter.dtoFromEntiy(topicVotingInserted);
	}

	public TopicVotingDTO openSession(TopicVotingDTO topicVotingDTO) {
		Optional<TopicVoting> topicVoting = topicVotingRepository.findById(topicVotingDTO.getTopicVotingId());
		if (topicVoting.isPresent()) {
			sessionService.openSession(topicVoting.get(), topicVotingDTO.getFinalVoting());
		}
		return topicVotingConverter.dtoFromEntiy(topicVoting.orElse(null));
	}

}
