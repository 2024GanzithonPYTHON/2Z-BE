package com.pj2z.pj2zbe.heart.entity;

import com.pj2z.pj2zbe.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Heart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String setting;
    private String choice;

    @Column(nullable = false)
    private String gptAnswer;

    @Builder
    public Heart(UserEntity user, String setting, String choice, String gptAnswer) {
        this.user = user;
        this.setting = setting;
        this.choice = choice;
        this.gptAnswer = gptAnswer;
    }
}
