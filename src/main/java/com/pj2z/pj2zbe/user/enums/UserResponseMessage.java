package com.pj2z.pj2zbe.user.enums;

public enum UserResponseMessage {
    SIGN_SUCCESS("User created successfully!"),
    SIGN_FAILURE("회원가입에 실패하였습니다."),
    INVALID_INPUT("잘못된 입력값입니다."),
    USER_NOT_FOUND("계정이 존재하지 않습니다."),
    LOGIN_SUCCESS("Login successful")
    ;


    private final String message;

    UserResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
