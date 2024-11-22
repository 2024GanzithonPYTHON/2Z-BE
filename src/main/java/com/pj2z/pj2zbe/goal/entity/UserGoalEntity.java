package com.pj2z.pj2zbe.goal.entity;

import com.pj2z.pj2zbe.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "USER_GOAL")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGoalEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = false)
    private GoalEntity goal;



}
