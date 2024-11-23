package com.pj2z.pj2zbe.heart.dto.response;

import com.pj2z.pj2zbe.heart.entity.Heart;
import lombok.Builder;

@Builder
public record HeartResponse(
        Long userId,
        String setting,
        String choice,
        String gptAnswer
) {
    public static HeartResponse from(Heart heart) {
        return HeartResponse.builder()
                .userId(heart.getUser().getId())
                .setting(heart.getSetting())
                .choice(heart.getChoice())
                .gptAnswer(heart.getGptAnswer())
                .build();
    }
}
