package com.pj2z.pj2zbe.heart.controller;

import com.pj2z.pj2zbe.common.template.RspTemplate;
import com.pj2z.pj2zbe.heart.dto.request.HeartSaveRequest;
import com.pj2z.pj2zbe.heart.dto.response.HeartResponse;
import com.pj2z.pj2zbe.heart.service.HeartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hearts")
public class HeartController {

    private final HeartService heartService;

    @PostMapping
    public RspTemplate<String> createHeart(@Valid @RequestBody HeartSaveRequest request) {
        heartService.saveHeart(request);
        return new RspTemplate<>(HttpStatus.CREATED, "성공적으로 좋아요를 눌렀습니다.");
    }

    @GetMapping
    public RspTemplate<List<HeartResponse>> getHeart() {
        List<HeartResponse> responses = heartService.retrieveAllHearts();
        return new RspTemplate<>(HttpStatus.OK, "성공적으로 좋아요를 조회했습니다.", responses);
    }

    @GetMapping("/{heartId}")
    public RspTemplate<HeartResponse> getHeart(@PathVariable Long heartId) {
        HeartResponse response = heartService.retrieveOneHeart(heartId);
        return new RspTemplate<>(HttpStatus.OK, "성공적으로 좋아요를 조회했습니다.", response);
    }

    @DeleteMapping("/{heartId}")
    public RspTemplate<String> deleteHeart(@PathVariable Long heartId) {
        heartService.deleteHeart(heartId);
        return new RspTemplate<>(HttpStatus.NO_CONTENT, "성공적으로 좋아요를 취소했습니다.");
    }

}
