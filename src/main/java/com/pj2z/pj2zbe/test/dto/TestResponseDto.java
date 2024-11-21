package com.pj2z.pj2zbe.test.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestResponseDto {
    private String status;
    private String message;
    private String timestamp;
    private Integer statusCode;
    private String path;
}
