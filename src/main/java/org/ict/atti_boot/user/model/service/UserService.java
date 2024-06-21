package org.ict.atti_boot.user.model.service;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.security.model.entity.TokenLogin;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.jpa.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
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
                .userName(user.getUserName())
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(encodedPassword)
                .gender(user.getGender())
                .userType(user.getUserType())
                .birthDate(user.getBirthDate())
                .phone(user.getPhone())
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
    @Transactional
    public User signUpSnsUser(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일이 이미 사용중입니다.");
                });
        return userRepository.save(user);
    }

    @Transactional
    public Optional<User> findById(String userId) {
        log.debug("Finding user by id: {}", userId);
        return userRepository.findByUserId(userId);
    }

    @Transactional
    public User updateUser(User user) {
        Optional<User> existingUserOpt = userRepository.findById(user.getUserId());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setNickName(user.getNickName());
            existingUser.setPhone(user.getPhone());
            existingUser.setGender(user.getGender());
            existingUser.setBirthDate(user.getBirthDate());
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Transactional
    public void deleteUser(String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    //파일업로드
    @Transactional
    public User uploadProfilePhoto(Long userId, MultipartFile file) throws IOException {
        Optional<User> userOpt = userRepository.findById(String.valueOf(userId));
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getProfileUrl() != null) {
                File existingFile = new File("uploads/" + user.getProfileUrl());
                if (existingFile.exists()) {
                    existingFile.delete();
                }
            }
            String newFileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            File newFile = new File("uploads/" + newFileName);
            file.transferTo(newFile);
            user.setProfileUrl(newFileName);
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    //파일 삭제
    @Transactional
    public void deleteProfilePhoto(Long userId) {
        Optional<User> userOpt = userRepository.findById(String.valueOf(userId));
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getProfileUrl() != null) {
                File existingFile = new File("uploads/" + user.getProfileUrl());
                if (existingFile.exists()) {
                    existingFile.delete();
                }
                user.setProfileUrl(null);
                userRepository.save(user);
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }
}

