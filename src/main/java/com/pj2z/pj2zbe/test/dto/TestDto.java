package com.pj2z.pj2zbe.test.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TestDto {
    private Long userId;
    private Map<String, Integer> testResults;
}
