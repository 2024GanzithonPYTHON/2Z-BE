package com.pj2z.pj2zbe.goal.repository;

import com.pj2z.pj2zbe.goal.entity.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<GoalEntity,Long> {
    Optional<GoalEntity> findByGoalName(String name);
}
