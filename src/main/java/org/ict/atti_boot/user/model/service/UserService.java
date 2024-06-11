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

//    @Transactional
//    public User signUpUser(User user) {
//        log.debug("Checking if email is already in use: {}", user.getEmail());
//        userRepository.findByEmail(user.getEmail())
//                .ifPresent(u -> {
//                    log.warn("Email already in use: {}", user.getEmail());
//                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일이 이미 사용중입니다.");
//                });
//        // 로그 추가하여 SELECT 쿼리 발생 원인 파악
//        log.debug("Creating new user: {}", user.getUserName());
//        User newUser = createUser(user);
//        // 새 유저 저장
//        User savedUser = userRepository.save(newUser);
//        log.debug("User created successfully with userId: {}", savedUser.getUserId());
//        // 불필요한 조회 로직이 있는지 확인
//        // findByUserId 메서드가 호출되지 않는지 확인
//        return savedUser;
//        //return userRepository.save(newUser);
//    }

    @Transactional
    public User signUpUser(User user) {
        log.debug("Checking if email is already in use: {}", user.getEmail());
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    log.warn("Email already in use: {}", user.getEmail());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일이 이미 사용중입니다.");
                });

        log.debug("Creating new user: {}", user.getUserName());
        User newUser = createUser(user);
        log.debug("Saving new user: {}", newUser);

        User savedUser = userRepository.save(newUser);
        log.debug("User created successfully with userId: {}", savedUser.getUserId());

        return savedUser;
    }
    private User createUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        return User.builder()
                .userId(UUID.randomUUID().toString())  // 유저 ID 생성
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(encodedPassword)
                .gender(user.getGender())
                .userType('U')
                .birthDate(user.getBirthDate())
                .build();
    }

    @Transactional
    public Optional<User> findByEmail(String email) {
        log.debug("Finding user by email: {}", email);
        return userRepository.findByEmail(email);
    }

    @Transactional
    public Optional<User> findByUserId(String userId) {
        log.debug("Finding user by userId: {}", userId);
        return userRepository.findByUserId(userId);
    }
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

}
