package com.pj2z.pj2zbe.heart.dto.request;

public record HeartSaveRequest(
        Long userId,
        String setting,
        String choice,
        String gptAnswer
) {

}
