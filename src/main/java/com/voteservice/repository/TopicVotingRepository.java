package com.voteservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.voteservice.model.TopicVoting;

@Repository
public interface TopicVotingRepository extends CrudRepository<TopicVoting, Long>{

}
