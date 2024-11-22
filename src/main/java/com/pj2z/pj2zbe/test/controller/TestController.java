package com.pj2z.pj2zbe.test.controller;

import com.pj2z.pj2zbe.test.dto.TestDto;
import com.pj2z.pj2zbe.test.dto.TestResponseDto;
import com.pj2z.pj2zbe.test.dto.TestUpdateDto;
import com.pj2z.pj2zbe.test.service.TestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class TestController {
    @Autowired
    TestService testService;

    @PostMapping("/tests/initial")
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

    @PostMapping("/personalities/update")
    public ResponseEntity<TestResponseDto> updateTestResults(@RequestBody TestUpdateDto updateDto){
        try {
            TestResponseDto response = testService.updateTestResults(updateDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (EntityNotFoundException e) {
            TestResponseDto errorResponse = TestResponseDto.builder()
                    .status("error")
                    .message("No test results found for the user")
                    .timestamp(new Timestamp(System.currentTimeMillis()).toString())
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .path("/personalities/update")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            TestResponseDto errorResponse = TestResponseDto.builder()
                    .status("error")
                    .message("An unexpected error occurred while updating test results")
                    .timestamp(new Timestamp(System.currentTimeMillis()).toString())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .path("/personalities/update")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
