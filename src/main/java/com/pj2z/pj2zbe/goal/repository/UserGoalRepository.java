package com.pj2z.pj2zbe.goal.repository;

import com.pj2z.pj2zbe.goal.entity.UserGoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserGoalRepository extends JpaRepository<UserGoalEntity,Long> {
    Optional<List<UserGoalEntity>> findAllByUserId(Long userId); // 복합 키 내부의 userId 기준 조회

}
