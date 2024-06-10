package org.ict.atti_boot.user.model.service;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.jpa.repository.UserRepository;
import org.ict.atti_boot.user.model.input.InputUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }


    @Transactional
    public User signUpUser(InputUser inputUser) {
        userRepository.findByEmail(inputUser.getEmail())
                .ifPresent(u -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일이 이미 사용중입니다.");
                });
        return userRepository.save(createUser(inputUser));
    }

    private User createUser(InputUser user) {   //회원가입
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        return User.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(encodedPassword)
                .userType('U')
                .build();
    }

    @Transactional
    public Optional<User> findByEmail(String username) {
        return userRepository.findByEmail(username);
    }



}
