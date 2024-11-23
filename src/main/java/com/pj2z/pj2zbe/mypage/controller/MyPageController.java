package com.pj2z.pj2zbe.mypage.controller;

import com.pj2z.pj2zbe.mypage.dto.MyPageDto;
import com.pj2z.pj2zbe.mypage.dto.MyPageResponseDto;
import com.pj2z.pj2zbe.mypage.service.MyPageService;
import com.pj2z.pj2zbe.test.dto.TestResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
public class MyPageController {
    @Autowired
    MyPageService myPageService;

    @GetMapping("/mypage")
    public ResponseEntity<MyPageResponseDto> getNickname(@RequestBody MyPageDto requestDto) {
        MyPageResponseDto response = myPageService.getUserNickname(requestDto.getUserId());
        return ResponseEntity.ok(response);
    }

}
