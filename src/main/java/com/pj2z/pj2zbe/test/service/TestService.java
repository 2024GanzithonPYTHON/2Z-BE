package com.pj2z.pj2zbe.test.service;

import com.pj2z.pj2zbe.test.dto.TestDto;
import com.pj2z.pj2zbe.test.dto.TestResponseDto;
import com.pj2z.pj2zbe.test.dto.TestUpdateDto;
import com.pj2z.pj2zbe.test.entity.Test;
import com.pj2z.pj2zbe.test.repository.TestRepository;
import com.pj2z.pj2zbe.user.entity.UserEntity;
import com.pj2z.pj2zbe.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class TestService {

    @Autowired
    TestRepository testRepository;

    @Autowired
    UserRepository userRepository;

    public TestResponseDto saveTestResults(TestDto requestDto) {
        UserEntity user = userRepository.findById(requestDto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));

//        List<Test> userTests = testRepository.findByUser(user);
//        for (Test test : userTests){
//            test.setUse_yn("N");
//        }
//        testRepository.saveAll(userTests);

        try {
            Map<String, Integer> testResults = requestDto.getTestResults();

            Test test = new Test();
            test.setUser(user);
            test.setExtroversion(testResults.getOrDefault("extroversion", 0));
            test.setDecision(testResults.getOrDefault("decision", 0));
            test.setRisk(testResults.getOrDefault("risk", 0));
            test.setComfort(testResults.getOrDefault("comfort", 0));
            test.setTime(testResults.getOrDefault("time", 0));
            test.setSocial(testResults.getOrDefault("social", 0));
            test.setBudget(testResults.getOrDefault("budget", 0));
            test.setUse_yn("Y");

            testRepository.save(test);

            return TestResponseDto.builder()
                    .status("success")
                    .message("Test results saved")
                    .timestamp(new Timestamp(System.currentTimeMillis()).toString())
                    .statusCode(200)
                    .path("/tests/initial")
                    .build();
        }  catch (Exception e) {
            throw new RuntimeException("Error occurred while saving test results", e);
        }
    }

    public TestResponseDto updateTestResults(TestUpdateDto updateDto){
        UserEntity user = userRepository.findById(updateDto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Test test = testRepository.findTopByUserOrderByCreatedAtDesc(user);
        if (test == null) {
            throw new EntityNotFoundException("No test results found for the user");
        }

        try{
            Map<String, Integer> updatedResults = updateDto.getUpdatedTestResults();
            test.setExtroversion(updatedResults.getOrDefault("extroversion", test.getExtroversion()));
            test.setDecision(updatedResults.getOrDefault("decision", test.getDecision()));
            test.setRisk(updatedResults.getOrDefault("risk", test.getRisk()));
            test.setComfort(updatedResults.getOrDefault("comfort", test.getComfort()));
            test.setTime(updatedResults.getOrDefault("time", test.getTime()));
            test.setSocial(updatedResults.getOrDefault("social", test.getSocial()));
            test.setBudget(updatedResults.getOrDefault("budget", test.getBudget()));

            testRepository.save(test);

            return TestResponseDto.builder()
                    .status("success")
                    .message("Test results updated successfully")
                    .timestamp(new Timestamp(System.currentTimeMillis()).toString())
                    .statusCode(200)
                    .path("/personalities/update")
                    .build();

        }catch (Exception e) {
            throw new RuntimeException("Error occurred while updating test results", e);
        }
    }
    public Test getTest(Long userId){
        return testRepository.findTopByUserIdOrderByCreatedAtDesc(userId);
    }


}