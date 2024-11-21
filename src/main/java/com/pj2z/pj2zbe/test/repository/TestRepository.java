package com.pj2z.pj2zbe.test.repository;

import com.pj2z.pj2zbe.test.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}
