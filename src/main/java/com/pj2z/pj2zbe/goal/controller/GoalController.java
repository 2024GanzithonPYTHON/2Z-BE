package com.pj2z.pj2zbe.goal.controller;

import com.pj2z.pj2zbe.goal.dto.GoalResponseDto;
import com.pj2z.pj2zbe.goal.dto.GoalUpdateDto;
import com.pj2z.pj2zbe.goal.dto.GoalYNUpdateDto;
import com.pj2z.pj2zbe.goal.entity.GoalEntity;
import com.pj2z.pj2zbe.goal.service.GoalService;
import com.pj2z.pj2zbe.user.dto.UserResponseDto;
import com.pj2z.pj2zbe.user.entity.UserEntity;
import com.pj2z.pj2zbe.user.enums.UserGoalYN;
import com.pj2z.pj2zbe.user.enums.UserResponseMessage;
import com.pj2z.pj2zbe.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")
public class GoalController {

    @Autowired
    GoalService goalService;
    @Autowired
    private UserService userService;

    @PostMapping("/update")
    public ResponseEntity<Object> userGoalUpdate(@RequestBody @Valid GoalUpdateDto updateDto){

        if(goalService.updateUserGoals(updateDto.getUserId(), updateDto.getGoals())){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new UserResponseDto(UserResponseMessage.SIGN_SUCCESS.getMessage(), updateDto.getUserId()));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("NO");

    }


    @GetMapping("/list/{userId}")
    public ResponseEntity<Object> userGoalGetList(@PathVariable Long userId){
        List<String> userGoals = goalService.getGoalsByUserId(userId);
        UserGoalYN userGoalYN = userService.getUserGoalYN(userId);
        if (userGoals != null && userGoalYN != null) {
            return ResponseEntity.ok(new GoalResponseDto(userGoals,userGoalYN));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping("/used")
    public ResponseEntity<Object> userGoalYNUpdate(@RequestBody @Valid GoalYNUpdateDto goalYNUpdateDto){
       UserEntity user = userService.getUserId(goalYNUpdateDto.getUserId());
       if( user == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
       }else {
           user.setUserGoalYN(goalYNUpdateDto.getGoalYN());
           if(userService.save(user)){
               return ResponseEntity.status(HttpStatus.OK).body("Goals update");
           }else {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
           }
       }

    }
}
