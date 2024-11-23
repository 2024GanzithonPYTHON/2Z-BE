package com.pj2z.pj2zbe.heart.service;

import com.pj2z.pj2zbe.common.exception.UserNotFoundException;
import com.pj2z.pj2zbe.heart.dto.request.HeartSaveRequest;
import com.pj2z.pj2zbe.heart.entity.Heart;
import com.pj2z.pj2zbe.heart.repository.HeartRepository;
import com.pj2z.pj2zbe.user.entity.UserEntity;
import com.pj2z.pj2zbe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HeartService {

    private final UserRepository userRepository;
    private final HeartRepository heartRepository;

    @Transactional
    public void saveHeart(HeartSaveRequest request) {
        UserEntity user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Heart heart = Heart.builder()
                .user(user)
                .setting(request.setting())
                .choice(request.choice())
                .gptAnswer(request.gptAnswer())
                .build();

        heartRepository.save(heart);
    }
}