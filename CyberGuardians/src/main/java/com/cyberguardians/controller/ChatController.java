package com.cyberguardians.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cyberguardians.entity.ChatBotRequest;
import com.cyberguardians.entity.ChatBotResponse;
import com.cyberguardians.entity.Message;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ChatController {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${openai.chatgtp.model}")
	private String model;

	@Value("${openai.chatgtp.max-completions}")
	private int maxCompletions;

	@Value("${openai.chatgtp.temperature}")
	private double temperature;

	@Value("${openai.chatgtp.max_tokens}")
	private int maxTokens;

	@Value("${openai.chatgtp.api.url}")
	private String apiUrl;

	@Value("${flask.server.url}")
	private String flaskServerUrl; // Flask 서버의 URL을 application.properties 등을 통해 주입받음

	@PostMapping("/chat")
	public ChatBotResponse chat(@RequestParam("prompt") String prompt) {

		ChatBotRequest request = new ChatBotRequest(model, Arrays.asList(new Message("user", prompt)), maxCompletions,
				temperature, maxTokens);
		System.out.println("chat : " + request);
		ChatBotResponse response = restTemplate.postForObject(apiUrl, request, ChatBotResponse.class);
		return response;
	}

	public int checkUrl(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// 전송할 데이터 설정
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("url", url);

		// HTTP 요청 설정
		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
		System.out.println("requestEntity : " + requestEntity);

		// REST 템플릿 생성
		RestTemplate restTemplate = new RestTemplate();

		// Flask 서버로 POST 요청 보내기
		ResponseEntity<String> response = restTemplate.postForEntity(flaskServerUrl, requestEntity, String.class);
		System.out.println("response : " + response);
		
		// 응답에서 결과 추출
		String result = response.getBody();
		System.out.println("result : " + result);
		if (result != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				JsonNode rootNode = objectMapper.readTree(result);
				JsonNode predictionNode = rootNode.get("prediction");
				if (predictionNode != null) {
					int predictionValue = predictionNode.asInt();
					System.out.println("predictionValue : " + predictionValue);
					return predictionValue;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	

}
