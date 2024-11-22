package com.pj2z.pj2zbe.recommend.controller;

import com.pj2z.pj2zbe.common.template.RspTemplate;
import com.pj2z.pj2zbe.recommend.dto.request.RecommendRequest;
import com.pj2z.pj2zbe.recommend.dto.response.RecommendResponse;
import com.pj2z.pj2zbe.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommends/request")
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping
    public RspTemplate<RecommendResponse> recommend(@RequestBody RecommendRequest request) throws IOException {
        RecommendResponse response = recommendService.getRecommendation(request);
        return new RspTemplate<>(HttpStatus.OK, "성공적으로 추천받았습니다.", response);
    }
}
