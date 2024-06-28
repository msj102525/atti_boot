package org.ict.atti_boot.user.model.service;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.inquiry.model.inquiryService.InquiryService;
import org.ict.atti_boot.security.repository.TokenLoginRepository;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.jpa.repository.SocialLoginRepository;
import org.ict.atti_boot.user.jpa.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final TokenLoginRepository tokenLoginRepository;
    private final SocialLoginRepository socialLoginRepository;
    private final JavaMailSender emailSender;
    private final InquiryService inquiryService;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, JavaMailSender javaMailSender, TokenLoginRepository tokenLoginRepository, SocialLoginRepository socialLoginRepository, JavaMailSender emailSender, InquiryService inquiryService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.tokenLoginRepository = tokenLoginRepository;
        this.emailSender = javaMailSender;
        this.socialLoginRepository = socialLoginRepository;
        this.inquiryService = inquiryService;
    }

    //회원가입처리
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


    //사용자 정보를 받아 새로운 사용자 객체를 생성하는 메소드
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

    //회원가입
    @Transactional
    public User signUpSnsUser(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일이 이미 사용중입니다.");
                });
        return userRepository.save(user);
    }

   //id로 유저정보
    @Transactional
    public Optional<User> findById(String userId) {
        log.debug("Finding user by id: {}", userId);
        return userRepository.findByUserId(userId);
    }

    // 회원 수정
//    @Transactional
//    public User updateUser(User user) {
//        Optional<User> existingUserOpt = userRepository.findById(user.getUserId());
//        if (existingUserOpt.isPresent()) {
//            User existingUser = existingUserOpt.get();
//            existingUser.setUserName(user.getUserName());
//            existingUser.setNickName(user.getNickName());
//            existingUser.setPhone(user.getPhone());
//            existingUser.setGender(user.getGender());
//            existingUser.setBirthDate(user.getBirthDate());
//            return userRepository.save(existingUser);
//        } else {
//            throw new RuntimeException("User not found");
//        }
//    }

    //일반회원
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

    //소셜 회원 정보수정
    @Transactional
    public User updateSocialUser(User user) {
        Optional<User> existingUserOpt = userRepository.findByEmail(user.getEmail());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            // 소셜 회원 정보 업데이트 로직
            existingUser.setUserName(user.getUserName());
            existingUser.setNickName(user.getNickName());
            existingUser.setPhone(user.getPhone());
            existingUser.setGender(user.getGender());
            existingUser.setBirthDate(user.getBirthDate());
            // 추가적인 소셜 네트워크 관련 정보 업데이트
//            existingUser.setSocialNetworkId(user.getSocialNetworkId());
//            existingUser.setSocialNetworkToken(user.getSocialNetworkToken());
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    //회원 탈퇴
    @Transactional
    public void deleteUser(String userId) {
        log.debug("Deleting user by id: {}", userId);
        if (userRepository.existsById(userId)) {
            // 연관된 데이터 삭제
            inquiryService.deleteInquiriesByUserId(userId);
            tokenLoginRepository.deleteByUserId(userId);
            socialLoginRepository.deleteByUserId(userId);
            log.debug("Related token login data for user {} deleted successfully", userId);

            // 사용자 삭제
            userRepository.deleteById(userId);
            log.debug("User {} deleted successfully", userId);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    //프로핑 사진 수정
    @Transactional
    public User uploadProfilePhoto(String userId, MultipartFile file) throws IOException {
        Optional<User> userOpt = userRepository.findById(userId);
        String uploadDir = "src/main/resources/hospitalprofile/";

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // 기존 파일 삭제
            if (user.getProfileUrl() != null) {
                File existingFile = new File(uploadDir + user.getProfileUrl());

                // 파일 존재 여부 및 삭제 시도
                if (existingFile.exists() && existingFile.isFile()) {
                    boolean deleted = existingFile.delete();
                    if (!deleted) {
                        throw new IOException("기존 프로필 사진을 삭제할 수 없습니다.");
                    }
                }
            }

            // 새 파일 이름 생성
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + fileExtension;

            // 저장 경로 생성
            Path filePath = Paths.get(uploadDir + newFileName);

            // 파일 저장
            Files.write(filePath, file.getBytes());

            // 저장된 파일 경로 설정
            newFileName = "/images/" + filePath.getFileName().toString();
            user.setProfileUrl(newFileName);

            // 사용자 정보 저장
            return userRepository.save(user);
        }

        throw new RuntimeException("User not found");
    }

  //프로필 사진 삭제
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

    //간단한 이메일 메시지 보내기
    @Transactional
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

   //사용자의 비밀번호 업데이트
    @Transactional
    public void updateUserPassword(String userId, String newPassword) {
        log.debug("Updating user password: {}", userId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            log.info("Updating password for user {} with new password {}", userId, newPassword);
            User user = userOptional.get();
            user.setPassword(bCryptPasswordEncoder.encode(newPassword)); // 비밀번호 암호화
            userRepository.save(user);
        }
    }

     //사용자 이름과 이메일을 통해 사용자 정보를 찾기
    public Optional<User> findByUserNameAndEmail(String userName, String email) {
        log.debug("Finding user by userName: {} and email: {}", userName, email);
        return userRepository.findByUserNameAndEmail(userName, email);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
