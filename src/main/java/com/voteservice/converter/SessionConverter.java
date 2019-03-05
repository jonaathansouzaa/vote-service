package com.voteservice.converter;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.voteservice.dto.SessionDTO;
import com.voteservice.model.Session;
import com.voteservice.model.TopicVoting;

@Component
public class SessionConverter {

	public Session sessionFromDto(TopicVoting topicVoting, LocalDateTime startingVoting, SessionDTO sessionDto) {
		Session session = new Session();
		session.setTopicVoting(topicVoting);
		session.setStartingVoting(startingVoting);
		session.setFinalVoting(buildFinalVoting(sessionDto.getFinalVoting(), startingVoting));
		return session;
	}

	private LocalDateTime buildFinalVoting(LocalDateTime finalVoting, LocalDateTime startingVoting) {
		if (Objects.isNull(finalVoting)) {
			return startingVoting.plusMinutes(1);
		}
		return finalVoting;
	}

	public SessionDTO dtoFromEntity(Session session) {
		return new SessionDTO(Objects.nonNull(session));
	}


}
