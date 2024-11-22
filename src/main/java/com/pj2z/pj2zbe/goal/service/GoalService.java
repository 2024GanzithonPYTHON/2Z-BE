package com.pj2z.pj2zbe.goal.service;

import com.pj2z.pj2zbe.goal.entity.GoalEntity;
import com.pj2z.pj2zbe.goal.entity.UserGoalEntity;
import com.pj2z.pj2zbe.goal.repository.GoalRepository;
import com.pj2z.pj2zbe.goal.repository.UserGoalRepository;
import com.pj2z.pj2zbe.user.entity.UserEntity;
import com.pj2z.pj2zbe.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoalService {

    @Autowired
    GoalRepository goalRepository;

    @Autowired
    UserGoalRepository  userGoalRepository;

    @Autowired
    UserRepository userRepository;


    public boolean updateUserGoals(Long userId, List<String> goalNames) {
        boolean result = false;
        try{
        // 1. 사용자와 관련된 기존 목표 목록을 가져옴
        Optional<List<UserGoalEntity>> optionalGoals = userGoalRepository.findAllByUserId(userId);

        List<UserGoalEntity> existingGoals = optionalGoals.orElse(new ArrayList<>());

        // 2. 기존 목표 중에 삭제해야 할 목표를 찾기
        Set<String> goalNamesSet = new HashSet<>(goalNames); // 새로 온 목표 목록
        Iterator<UserGoalEntity> iterator = existingGoals.iterator();
        while (iterator.hasNext()) {
            UserGoalEntity userGoalEntity = iterator.next();
            String existingGoalName = userGoalEntity.getGoal().getGoalName(); // GoalEntity의 이름
            if (!goalNamesSet.contains(existingGoalName)) {
                // 새로 온 목표 목록에 없으면 삭제
                userGoalRepository.delete(userGoalEntity);
                iterator.remove();
            }
        }

        // 3. 새로 추가해야 할 목표를 찾아 추가
        for (String goalName : goalNames) {
            boolean isGoalExist = existingGoals.stream()
                    .anyMatch(userGoal -> userGoal.getGoal().getGoalName().equals(goalName));

            if (!isGoalExist) {
                // goalName에 해당하는 GoalEntity를 찾아서 추가
                GoalEntity goalEntity = goalRepository.findByGoalName(goalName)
                        .orElseThrow(() -> new IllegalArgumentException("Goal not found: " + goalName));
               UserEntity user = userRepository.findById(userId).get();
                UserGoalEntity userGoalEntity =  UserGoalEntity.builder()
                        .user(user)
                        .goal(goalEntity)
                        .build();

                userGoalRepository.save(userGoalEntity); // 새로운 UserGoalEntity 저장
            }
        }
            result = true;
        }
        catch (Exception e){}

        return result;
    }

    public List<String> getGoalsByUserId(Long userId) {
        // usergoal 테이블에서 userId에 해당하는 모든 UserGoalEntity를 조회
        try {
            Optional<List<UserGoalEntity>> optionalGoals = userGoalRepository.findAllByUserId(userId);

            List<UserGoalEntity> userGoals = optionalGoals.orElse(new ArrayList<>());
            if (userGoals.isEmpty()) {
                return new ArrayList<String>();
            }

            // UserGoalEntity에서 GoalEntity만 추출
            List<String> goals = new ArrayList<>();
            for (UserGoalEntity userGoal : userGoals) {
                goals.add(userGoal.getGoal().getGoalName());
            }
            return goals;
        }
        catch (Exception e){

        }
        return null;
    }


}
