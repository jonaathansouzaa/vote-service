package com.voteservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.voteservice.model.Session;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long>{

}
