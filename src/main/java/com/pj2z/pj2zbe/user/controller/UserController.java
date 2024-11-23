package com.pj2z.pj2zbe.user.controller;

import com.pj2z.pj2zbe.user.dto.UserJoinDto;
import com.pj2z.pj2zbe.user.dto.UserLoginDto;
import com.pj2z.pj2zbe.user.dto.UserResponseDto;
import com.pj2z.pj2zbe.user.entity.UserEntity;
import com.pj2z.pj2zbe.user.enums.UserResponseMessage;
import com.pj2z.pj2zbe.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    /************************************************************************************
     * 함  수  명      : userJoin
     * 내      용      : 회원가입
     * 설      명      :
     ************************************************************************************/
    @PostMapping("/join")
    public ResponseEntity<Object> userJoin(@RequestBody @Valid UserJoinDto userJoinDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getAllErrors().forEach(error ->
                    errorMessages.append(error.getDefaultMessage()).append("\n")
            );
            return ResponseEntity.badRequest().body(errorMessages.toString());
        }


        if (userService.register(userJoinDto.toUserEntity())) {
            Long userId = userService.getUserEmail(userJoinDto.getEmail()).getId();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new UserResponseDto(UserResponseMessage.SIGN_SUCCESS.getMessage(), userId,userJoinDto.getNickname()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(UserResponseMessage.SIGN_FAILURE.getMessage());
        }
    }
    /************************************************************************************
     * 함  수  명      : userLogin
     * 내      용      : 로그인
     * 설      명      :
     ************************************************************************************/
    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody @Valid UserLoginDto userLoginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getAllErrors().forEach(error ->
                    errorMessages.append(error.getDefaultMessage()).append("\n")
            );
            return ResponseEntity.badRequest().body(errorMessages.toString());
        }

        UserEntity user  = userService.authenticate(userLoginDto.getEmail()
                                                   , userLoginDto.getPassword());

        if (user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(UserResponseMessage.USER_NOT_FOUND.getMessage());
        }else {
            return ResponseEntity
                    .ok(new UserResponseDto(UserResponseMessage.LOGIN_SUCCESS.getMessage()
                                                , user.getId(), user.getNickname()));
        }

    }

}
