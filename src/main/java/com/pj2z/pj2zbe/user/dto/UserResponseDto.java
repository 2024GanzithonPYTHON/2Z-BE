package com.pj2z.pj2zbe.user.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {
    private String message;
    private Long userId;

    public UserResponseDto(String message, Long userId) {

        this.message = message;
        this.userId = userId;
    }

    public UserResponseDto(String message) {
        this.message = message;
        this.userId = null;
    }


}
