package com.pj2z.pj2zbe.recommend.service;

import com.pj2z.pj2zbe.recommend.dto.response.RecommendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatGPTService {

    @Value("${openai.api.key}")
    private String openAIKey;

    @Value("${openai.api.url}")
    private String apiURL;

    @Value("${openai.model}")
    private String model;

    private final RestTemplate restTemplate;

    public RecommendResponse getRecommendation(String prompt) {
        Map<String, Object> gptRequest = Map.of(
                "model", model,
                "messages", List.of(Map.of(
                        "role", "user",
                        "content", prompt
                )),
                "stream", false
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAIKey);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(gptRequest, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(apiURL, requestEntity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return parseGPTResponse(response.getBody());
        } else {
            throw new RuntimeException("GPT 응답 오류");
        }
    }

    private RecommendResponse parseGPTResponse(Map<String, Object> gptResponse) {
        Map<String, Object> choice = (Map<String, Object>) ((List<?>) gptResponse.get("choices")).get(0);
        String content = (String) ((Map<String, Object>) choice.get("message")).get("content");

        String[] parts = content.split("\n", 2);
        String suggestion = parts[0].replace("추천 :", "").trim();
        String reason = parts[1].replace("추천 이유 :", "").trim();

        return new RecommendResponse(suggestion, reason);
    }
}

