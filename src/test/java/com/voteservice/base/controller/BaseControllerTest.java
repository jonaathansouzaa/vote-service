package com.voteservice.base.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseControllerTest {

	public InputStream loadResourceAsInputStream(String pathAsString) {
		try {
			return new ClassPathResource(pathAsString).getInputStream();
		} catch (IOException e) {
			throw new RuntimeException("error loading resource " + pathAsString, e);
		}
	}

    public JsonNode loadAsJsonFromResource(String jsonFilePath) {
		try {
			return new ObjectMapper().readTree(loadResourceAsInputStream(jsonFilePath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String loadResourceAsString(String pathAsString) {
		try {
			return IOUtils.toString(loadResourceAsInputStream(pathAsString), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException("error loading resource " + pathAsString, e);
		}
	}
	
}
