package com.pj2z.pj2zbe.user.service;

import com.pj2z.pj2zbe.user.entity.UserEntity;
import com.pj2z.pj2zbe.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public void save(UserEntity user) {
        userRepository.save(user);
    }

    /**
     *
     * @param email
     * @return userEntity
     */
    public UserEntity getUserEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


    /************************************************************************************
     * 함  수  명      : authenticate
     * 내      용      : 사용자 인증
     * 설      명      :
     ************************************************************************************/
    public UserEntity authenticate(String email, String password) {
        Optional<UserEntity> user = userRepository.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.orElse(null);
        }
        return null;
    }

    /**
     * 함  수  명 : register
     * 내      용 : 회원가입
     * @param user
     * @return boolean(가입 여부 )
     */
    public boolean register(UserEntity user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return false;
        }
        userRepository.save(user);
        return true;
    }
}
