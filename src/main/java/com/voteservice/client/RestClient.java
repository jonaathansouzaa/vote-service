package com.voteservice.client;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.voteservice.client.response.UserInfoResponse;
import com.voteservice.exception.Messages;
import com.voteservice.exception.NotFoundException;

@Component
public class RestClient {
	
	@Value("${user.info.url}")
	private String url;

	private RestTemplate restTemplate;

	@Autowired
	public RestClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Boolean validateDocument(String document) {
		try {
			URI uri = URI.create(String.format(url, document));
			UserInfoResponse userInfoResponse = restTemplate.getForObject(uri, UserInfoResponse.class);
			return userInfoResponse.isAbleToVote();
		} catch (HttpClientErrorException httpClientErrorException) {
			throw new NotFoundException(Messages.THE_DOCUMENT_NOT_FOUND);
		}
	}

}
