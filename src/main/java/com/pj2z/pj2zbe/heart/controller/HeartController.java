package com.pj2z.pj2zbe.heart.controller;

import com.pj2z.pj2zbe.common.template.RspTemplate;
import com.pj2z.pj2zbe.heart.dto.request.HeartSaveRequest;
import com.pj2z.pj2zbe.heart.service.HeartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hearts")
public class HeartController {
    private final HeartService heartService;

    // 저장, 조회, 삭제
    @PostMapping
    public RspTemplate<String> createHeart(@Valid @RequestBody HeartSaveRequest request) {
        heartService.saveHeart(request);
        return new RspTemplate<>(HttpStatus.CREATED, "성공적으로 좋아요를 눌렀습니다.");
    }

}
