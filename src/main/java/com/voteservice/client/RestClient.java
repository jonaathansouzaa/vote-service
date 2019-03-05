package com.voteservice.client;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.voteservice.client.response.UserInfoResponse;
import com.voteservice.config.VoteConfig;
import com.voteservice.exception.Messages;
import com.voteservice.exception.NotFoundException;

@Component
public class RestClient {
	
	private RestTemplate restTemplate;
	private VoteConfig voteConfig;

	@Autowired
	public RestClient(RestTemplate restTemplate, VoteConfig voteConfig) {
		this.restTemplate = restTemplate;
		this.voteConfig = voteConfig;
	}

	public Boolean validateDocument(String document) {
		try {
			URI uri = URI.create(String.format(voteConfig.getUrl(), document));
			UserInfoResponse userInfoResponse = restTemplate.getForObject(uri, UserInfoResponse.class);
			return userInfoResponse.isAbleToVote();
		} catch (HttpClientErrorException httpClientErrorException) {
			throw new NotFoundException(Messages.THE_DOCUMENT_NOT_FOUND);
		}
	}

}
