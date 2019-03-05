package com.voteservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.voteservice.model.Session;
import com.voteservice.model.TopicVoting;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long>{

	Optional<Session> findByTopicVoting(TopicVoting topicVoting);

}
