package com.pj2z.pj2zbe.test.entity;

import com.pj2z.pj2zbe.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "Test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "created_at",nullable = false)
    private Timestamp created_At;

    @Column(nullable = false)
    private Integer extroversion;

    @Column(nullable = false)
    private Integer decision;

    @Column(nullable = false)
    private Integer risk;

    @Column(nullable = false)
    private Integer comfort;

    @Column(nullable = false)
    private Integer time;

    @Column(nullable = false)
    private Integer social;

    @Column(nullable = false)
    private Integer budget;

    @PrePersist
    public void prePersist() {
        this.created_At = new Timestamp(System.currentTimeMillis());
    }
}
