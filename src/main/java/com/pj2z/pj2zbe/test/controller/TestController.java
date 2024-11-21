package com.pj2z.pj2zbe.test.controller;

import com.pj2z.pj2zbe.test.dto.TestDto;
import com.pj2z.pj2zbe.test.dto.TestResponseDto;
import com.pj2z.pj2zbe.test.service.TestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/tests")
public class TestController {
    @Autowired
    TestService testService;

    @PostMapping("/initial")
    public ResponseEntity<TestResponseDto> saveTestResults(@RequestBody TestDto requestDto){
        try{
            TestResponseDto response = testService.saveTestResults(requestDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            // 사용자 없음 오류 처리
            TestResponseDto errorResponse = TestResponseDto.builder()
                    .status("error")
                    .message("Invalid test results format")
                    .timestamp(new Timestamp(System.currentTimeMillis()).toString())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .path("/test/initial")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            TestResponseDto errorResponse = TestResponseDto.builder()
                    .status("error")
                    .message("An unexpected error occurred")
                    .timestamp(new Timestamp(System.currentTimeMillis()).toString())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .path("/test/initial")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
