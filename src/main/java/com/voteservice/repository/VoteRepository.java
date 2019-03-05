package com.voteservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.voteservice.model.TopicVoting;
import com.voteservice.model.Vote;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long>{

	Long countByTopicVotingAndVoteTrue(TopicVoting topicVoting);

	Long countByTopicVotingAndVoteFalse(TopicVoting topicVoting);

}
