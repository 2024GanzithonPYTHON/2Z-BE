package com.pj2z.pj2zbe.user.entity;

import com.pj2z.pj2zbe.user.enums.UserGoalYN;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity(name = "USER")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false,name = "GOAL_YN")
    @Enumerated(EnumType.STRING)
    private UserGoalYN userGoalYN;

    @Column(name = "created_at")
    private Timestamp created_At; //계정 생성일

    @Column(name = "updated_at")
    private Timestamp updated_At; //계정 수정시간

    @PrePersist
    public void prePersist() {
        this.created_At = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void preUpdate() {
        this.updated_At = new Timestamp(System.currentTimeMillis());
    }


    public void setUserGoalYN(UserGoalYN userGoalYN) {
        this.userGoalYN = userGoalYN;
    }
}
