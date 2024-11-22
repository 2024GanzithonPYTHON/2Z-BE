package com.pj2z.pj2zbe.goal.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class GoalUpdateDto {

    @NotNull(message = "USERID는 필수입니다.")
    private Long userId;

    private List<String> goals;

}
