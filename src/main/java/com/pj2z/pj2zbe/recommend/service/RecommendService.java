package com.pj2z.pj2zbe.recommend.service;

import com.pj2z.pj2zbe.recommend.dto.request.ChatGPTRequest;
import com.pj2z.pj2zbe.recommend.dto.request.RecommendRequest;
import com.pj2z.pj2zbe.recommend.dto.response.ChatGPTResponse;
import com.pj2z.pj2zbe.recommend.dto.response.RecommendResponse;
import com.pj2z.pj2zbe.test.entity.Test;
import com.pj2z.pj2zbe.test.repository.TestRepository;
import com.pj2z.pj2zbe.user.entity.UserEntity;
import com.pj2z.pj2zbe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;@Slf4j

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final TestRepository testRepository;

    @Value("${openai.api.url}")
    private String apiURL;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.prompt}")
    private String promptTemplate;

    public RecommendResponse getRecommendation(RecommendRequest request) {
        UserEntity user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Test test = testRepository.findByUserId(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("Test not found"));

        String prompt = createPrompt(request, test, user);
        ChatGPTResponse gptResponse = sendRequestToGPT(prompt);

        return formatGPTResponse(gptResponse);
    }

    private String createPrompt(RecommendRequest request, Test test, UserEntity user) {
        return String.format(promptTemplate,
                String.join(", ", request.choices()),
                request.setting(),
                test.getExtroversion(),
                test.getDecision(),
                test.getRisk(),
                test.getComfort(),
                test.getTime(),
                test.getSocial(),
                test.getBudget()
        );
    }

    private ChatGPTResponse sendRequestToGPT(String prompt) {
        log.info("prompt: {}", prompt);
        ChatGPTRequest chatGPTRequest = ChatGPTRequest.fromPrompt(model, prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ChatGPTRequest> requestEntity = new HttpEntity<>(chatGPTRequest, headers);
        ResponseEntity<ChatGPTResponse> response = restTemplate.postForEntity(apiURL, requestEntity, ChatGPTResponse.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("GPT API 응답받기를 실패했습니다.");
        }
        return response.getBody();
    }

    private RecommendResponse formatGPTResponse(ChatGPTResponse gptResponse) {
        String content = gptResponse.choices().get(0).message().content();
        String[] splitContent = content.split("\n", 2);

        String suggestion = splitContent[0].trim();
        String reason = splitContent.length > 1 ? splitContent[1].trim() : "";

        return new RecommendResponse(suggestion, reason);
    }
}
