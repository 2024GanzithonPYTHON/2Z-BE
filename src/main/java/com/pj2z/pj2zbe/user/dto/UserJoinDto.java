package com.pj2z.pj2zbe.user.dto;

import com.pj2z.pj2zbe.user.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserJoinDto {


    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호은 필수입니다.")
    @Size(min = 5, message = "비밀번호는 5자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 2, message = "닉네임은 2글자 이상이어야 합니다.")
    private String nickname;

    public UserEntity toUserEntity() {
       return UserEntity.builder()
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .build();
    }
}
