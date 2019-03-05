package com.voteservice.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voteservice.converter.SessionConverter;
import com.voteservice.dto.SessionDTO;
import com.voteservice.exception.Messages;
import com.voteservice.model.Session;
import com.voteservice.model.TopicVoting;
import com.voteservice.repository.SessionRepository;

@Service
public class SessionService {

	private SessionRepository sessionRepository;
	private SessionConverter sessionConverter;
	private TopicVotingService topicVotingService;

	@Autowired
	public SessionService(SessionRepository sessionRepository, SessionConverter sessionConverter, TopicVotingService topicVotingService) {
		this.sessionRepository = sessionRepository;
		this.sessionConverter = sessionConverter;
		this.topicVotingService = topicVotingService;
	}
	
	public SessionDTO openSession(SessionDTO sessionDto) {
		Optional<TopicVoting> topicVoting = topicVotingService.findById(sessionDto.getTopicVotingId());
		if (topicVoting.isPresent()) {
			Session session = sessionConverter.sessionFromDto(topicVoting.get(), LocalDateTime.now(), sessionDto);
			Session sessionSaved = sessionRepository.save(session);
			return sessionConverter.dtoFromEntity(sessionSaved);
		}
		throw new IllegalArgumentException(Messages.THE_TOPIC_VOTING_NOT_EXISTS);
	}
	
	public Boolean isSessionOpenOfTopicVoting(TopicVoting topicVoting) {
		Optional<Session> optionalSession = sessionRepository.findByTopicVoting(topicVoting);
		if (optionalSession.isPresent()) {
			return LocalDateTime.now().isBefore(optionalSession.get().getFinalVoting());
		}
		throw new IllegalArgumentException(Messages.THE_SESSION_NOT_EXISTS);
	}

}
