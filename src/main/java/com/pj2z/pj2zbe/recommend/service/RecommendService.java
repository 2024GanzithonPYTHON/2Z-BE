package com.pj2z.pj2zbe.recommend.service;

import com.pj2z.pj2zbe.common.exception.TestNotFoundException;
import com.pj2z.pj2zbe.common.exception.UserNotFoundException;
import com.pj2z.pj2zbe.goal.entity.GoalEntity;
import com.pj2z.pj2zbe.goal.entity.UserGoalEntity;
import com.pj2z.pj2zbe.goal.repository.GoalRepository;
import com.pj2z.pj2zbe.goal.repository.UserGoalRepository;
import com.pj2z.pj2zbe.recommend.dto.request.ChatGPTRequest;
import com.pj2z.pj2zbe.recommend.dto.request.RecommendRequest;
import com.pj2z.pj2zbe.recommend.dto.response.ChatGPTResponse;
import com.pj2z.pj2zbe.recommend.dto.response.RecommendResponse;
import com.pj2z.pj2zbe.test.entity.Test;
import com.pj2z.pj2zbe.test.repository.TestRepository;
import com.pj2z.pj2zbe.user.entity.UserEntity;
import com.pj2z.pj2zbe.user.enums.UserGoalYN;
import com.pj2z.pj2zbe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final TestRepository testRepository;
    private final GoalRepository goalRepository;
    private final UserGoalRepository userGoalRepository;

    @Value("${openai.api.url}")
    private String apiURL;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.prompt}")
    private String promptTemplate;

    public RecommendResponse getRecommendation(RecommendRequest request) {
        UserEntity user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Test test = testRepository.findByUserId(request.userId())
                .orElseThrow(() -> new TestNotFoundException("Test not found"));

        List<String> goalNames = fetchUserGoals(user, request.userId());

        String prompt = createPrompt(request, test, goalNames);
        ChatGPTResponse gptResponse = sendRequestToGPT(prompt);

        return formatGPTResponse(gptResponse);
    }

    private List<String> fetchUserGoals(UserEntity user, Long userId) {
        if (user.getUserGoalYN() == UserGoalYN.N) {
            return null;
        }
        return userGoalRepository.findAllByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No goals found for user"))
                .stream()
                .map(UserGoalEntity::getGoal)
                .map(GoalEntity::getGoalName)
                .toList();
    }

    private String createPrompt(RecommendRequest request, Test test, List<String> goalNames) {
        return String.format(
                promptTemplate,
                String.join(", ", request.choices()),
                request.setting(),
                goalNames != null ? String.join(", ", goalNames) : "",
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
        log.info("GPT한테 보내는 prompt 내용: {}", prompt);
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
