package com.pj2z.pj2zbe.goal.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "GOAL")
@Getter
public class GoalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String goalName;

    @Column
    private String category;

}
