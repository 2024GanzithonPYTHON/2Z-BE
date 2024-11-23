package com.pj2z.pj2zbe.test.entity;

import com.pj2z.pj2zbe.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "Test")
public class Test extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    @Min(0)
    @Max(100)
    private Integer extroversion;

    @Column(nullable = false)
    @Min(0)
    @Max(100)
    private Integer decision;

    @Column(nullable = false)
    @Min(0)
    @Max(100)
    private Integer risk;

    @Column(nullable = false)
    @Min(0)
    @Max(100)
    private Integer comfort;

    @Column(nullable = false)
    @Min(0)
    @Max(100)
    private Integer time;

    @Column(nullable = false)
    @Min(0)
    @Max(100)
    private Integer social;

    @Column(nullable = false)
    @Min(0)
    @Max(100)
    private Integer budget;

    @Column(nullable = false)
    @ColumnDefault("'Y'")
    private String use_yn;

}
