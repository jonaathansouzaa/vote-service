package com.voteservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voteservice.converter.VoteConverter;
import com.voteservice.dto.VoteDTO;
import com.voteservice.exception.ClosedSessionException;
import com.voteservice.exception.Messages;
import com.voteservice.model.TopicVoting;
import com.voteservice.model.Vote;
import com.voteservice.repository.VoteRepository;

@Service
public class VoteService {

	private TopicVotingService topicVotingService;
	private SessionService sessionService;
	private VoteConverter voteConverter;
	private VoteRepository voteRepository;

	@Autowired
	public VoteService(TopicVotingService topicVotingService, SessionService sessionService, VoteConverter voteConverter, VoteRepository voteRepository) {
		this.topicVotingService = topicVotingService;
		this.sessionService = sessionService;
		this.voteConverter = voteConverter;
		this.voteRepository = voteRepository;
	}
	
	public VoteDTO vote(VoteDTO voteDto) {
		Optional<TopicVoting> optionalTopicVoting = topicVotingService.findById(voteDto.getTopicVotingId());
		if (optionalTopicVoting.isPresent()) {
			if (sessionService.isSessionOpenOfTopicVoting(optionalTopicVoting.get())) {
				Vote vote = voteConverter.entityFromDto(optionalTopicVoting.get(), voteDto);
				Vote voteSaved = voteRepository.save(vote);
				return voteConverter.dtoFromEntity(voteSaved);
			}
			throw new ClosedSessionException(Messages.THE_SESSION_IS_CLOSED);
		}
		throw new IllegalArgumentException(Messages.THE_TOPIC_VOTING_NOT_EXISTS);
	}

}
