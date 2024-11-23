package com.pj2z.pj2zbe.recommend.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record ChatGPTRequest (
        String model,
        List<Message> messages
) {

    public static ChatGPTRequest fromPrompt(String model, String prompt) {
        return ChatGPTRequest.builder()
                .model(model)
                .messages(List.of(new Message("user", prompt)))
                .build();
    }
}
