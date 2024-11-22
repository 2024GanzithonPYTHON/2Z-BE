package com.pj2z.pj2zbe.recommend.service;

import com.pj2z.pj2zbe.recommend.dto.request.RecommendRequest;
import com.pj2z.pj2zbe.recommend.dto.response.RecommendResponse;
import com.pj2z.pj2zbe.test.entity.Test;
import com.pj2z.pj2zbe.test.repository.TestRepository;
import com.pj2z.pj2zbe.user.entity.UserEntity;
import com.pj2z.pj2zbe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendService {

    private final ChatGPTService chatGPTService;
    private final UserRepository userRepository;
    private final TestRepository testRepository;

    @Value("${openai.prompt}")
    private String promptTemplate;

    public RecommendResponse getRecommendation(RecommendRequest request) {
        UserEntity user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Test test = testRepository.findByUserId(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("테스트 데이터를 찾을 수 없습니다."));

        String prompt = createPrompt(request, user, test);

        return chatGPTService.getRecommendation(prompt);
    }

    private String createPrompt(RecommendRequest request, UserEntity user, Test test) {
        return String.format(
                promptTemplate,
                request.setting(),
                test.getExtroversion(),
                test.getDecision(),
                test.getRisk(),
                test.getComfort(),
                test.getTime(),
                test.getSocial(),
                test.getBudget(),
                String.join("\n", request.choices())
        );
    }
}
