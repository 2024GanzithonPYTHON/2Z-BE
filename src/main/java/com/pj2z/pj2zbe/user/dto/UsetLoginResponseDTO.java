package com.pj2z.pj2zbe.user.dto;

import lombok.Getter;



@Getter
public class UsetLoginResponseDTO {
    private String message;
    private Long userId;
    private String nickname;
    private String testYN;

    public UsetLoginResponseDTO(String message, Long userId) {
        this.message = message;
        this.userId = userId;
    }

    public UsetLoginResponseDTO(String message, Long userId, String nickname,String testYN) {
        this.message = message;
        this.userId = userId;
        this.nickname = nickname;
        this.testYN = testYN;
    }
    public UsetLoginResponseDTO(String message, Long userId, String nickname) {
        this.message = message;
        this.userId = userId;
        this.nickname = nickname;
        this.testYN = "N";
    }
    public UsetLoginResponseDTO(String message) {
        this.message = message;
        this.userId = null;
        this.testYN = "N";
    }


}

