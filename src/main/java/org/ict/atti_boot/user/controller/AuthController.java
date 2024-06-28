package org.ict.atti_boot.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.security.jwt.util.JWTUtil;
import org.ict.atti_boot.security.model.entity.TokenLogin;
import org.ict.atti_boot.security.service.TokenLoginService;
import org.ict.atti_boot.user.jpa.entity.SocialLogin;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.jpa.repository.SocialLoginRepository;
import org.ict.atti_boot.user.jpa.repository.UserRepository;
import org.ict.atti_boot.user.model.output.CustomUserDetails;
import org.ict.atti_boot.user.model.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Value("${kakao.client-id}")
    private String kakaoClientId;

    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${kakao.redirect-signup-uri}")
    private String kakaoRedirectSignupUri;

    @Value("${naver.client-id}")
    private String naverClientId;

    @Value("${naverClientSecret}")
    private String naverClientSecret;

    @Value("${naver.redirect-uri}")
    private String naverRedirectUri;

    @Value("${naver.redirect-signup-uri}")
    private String naverRedirectSignupUri;
    @Value("${kakaoApiHost}")
    private  String kakaoApiHost;

    private final UserRepository userRepository;
    private final SocialLoginRepository socialLoginRepository;
    private final UserService userService;
    private final TokenLoginService tokenLoginService;
    private final JWTUtil jwtUtil;

    public AuthController(UserRepository userRepository, SocialLoginRepository socialLoginRepository, UserService userService, TokenLoginService tokenLoginService, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.tokenLoginService = tokenLoginService;
        this.socialLoginRepository = socialLoginRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/kakao/callback")
    public void kakaoLogin(@RequestParam String code, HttpServletResponse response) throws IOException, JSONException {
        log.info("code = {}", code);

        // 액세스 토큰을 요청하기 위한 URL 및 헤더 설정
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String tokenRequestBody = "grant_type=authorization_code"
                + "&client_id=" + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectUri
                + "&code=" + code;

        // 토큰 요청
        HttpEntity<String> tokenRequestEntity = new HttpEntity<>(tokenRequestBody, tokenHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.POST, tokenRequestEntity, String.class);
        log.info("token response = {}", tokenResponse.getBody());

        JSONObject tokenJson = new JSONObject(tokenResponse.getBody());
        String accessToken = tokenJson.getString("access_token");
        log.info("accessToken = {}", accessToken);

        // 사용자 정보를 요청하기 위한 URL 및 헤더 설정
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.set("Authorization", "Bearer " + accessToken);

        // 사용자 정보 요청
        HttpEntity<String> userInfoRequestEntity = new HttpEntity<>(userInfoHeaders);
        ResponseEntity<String> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userInfoRequestEntity, String.class);
        log.info("user info response = {}", userInfoResponse.getBody());

        JSONObject userJson = new JSONObject(userInfoResponse.getBody());
        String email = userJson.getJSONObject("kakao_account").has("email") ?
                userJson.getJSONObject("kakao_account").getString("email") : null;
        log.info("email = {}", email);

        if (email == null) {
            log.error("카카오에서 이메일 정보를 제공하지 않습니다.");
            response.sendRedirect("http://localhost:3000/login"); // 실패 시 로그인 페이지로 리다이렉트
            return;
        }

        Optional<User> optionalUser = userRepository.findByEmailAndLoginType(email, "kakao");

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // 사용자의 카카오 액세스 토큰 업데이트
            user.setSnsAccessToken(accessToken);
            userRepository.save(user);


            Long accessExpiredMs = 600000L; // 10분
            String accessTokenJwt = jwtUtil.generateToken(email, "access", accessExpiredMs);
            Long refreshExpiredMs = 7200000L; // 2시간
            String refreshTokenJwt = jwtUtil.generateToken(email, "refresh", refreshExpiredMs);

            // TokenLogin 엔티티 저장
            TokenLogin tokenLogin = TokenLogin.builder()
                    .userId(user.getUserId())
                    .accessToken(accessTokenJwt)
                    .refreshToken(refreshTokenJwt)
                    .accessCreated(LocalDateTime.now())
                    .accessExpires(LocalDateTime.now().plusMinutes(accessExpiredMs / 60000))
                    .refreshCreated(LocalDateTime.now())
                    .refreshExpires(LocalDateTime.now().plusMinutes(refreshExpiredMs / 60000))
                    .user(user)
                    .build();
            tokenLoginService.save(tokenLogin);

            // 로그인 성공 후 URL에 토큰 정보 포함하여 리다이렉트
            String redirectUrl = String.format("http://localhost:3000/login/success?access=%s&refresh=%s&userId=%s&email=%s&userName=%s&nickName=%s&phone=%s&birthday=%s&gender=%s&loginType=%s",
                    accessTokenJwt, refreshTokenJwt, user.getUserId(), user.getEmail(), user.getUserName(), user.getNickName(), user.getPhone(), user.getBirthDate(), user.getGender(),user.getLoginType());
            response.sendRedirect(redirectUrl);
            log.info("로그인 성공: {}", email);
        } else {
            // 신규 사용자 회원가입 필요
            log.info("회원가입 필요: {}", email);
            response.sendRedirect("http://localhost:3000/signup"); // 회원가입 페이지로 리다이렉트
        }
    }


    //카카오 회원 가입
    @GetMapping("/kakao/signup/callback")
    public void kakaoSignup(@RequestParam String code, HttpServletResponse response) throws IOException, JSONException {
        log.info("code signup = {}", code);

        // 액세스 토큰을 요청하기 위한 URL 및 헤더 설정
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String tokenRequestBody = "grant_type=authorization_code"
                + "&client_id=" + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectSignupUri
                + "&code=" + code;

        // 토큰 요청
        HttpEntity<String> tokenRequestEntity = new HttpEntity<>(tokenRequestBody, tokenHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.POST, tokenRequestEntity, String.class);
        log.info("token response = {}", tokenResponse.getBody());

        JSONObject tokenJson = new JSONObject(tokenResponse.getBody());
        String accessToken = tokenJson.getString("access_token");
        log.info("accessToken = {}", accessToken);

        // 사용자 정보를 요청하기 위한 URL 및 헤더 설정
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.set("Authorization", "Bearer " + accessToken);

        // 사용자 정보 요청
        HttpEntity<String> userInfoRequestEntity = new HttpEntity<>(userInfoHeaders);
        ResponseEntity<String> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userInfoRequestEntity, String.class);
        log.info("user info response = {}", userInfoResponse.getBody());

        JSONObject userJson = new JSONObject(userInfoResponse.getBody());
        String email = userJson.getJSONObject("kakao_account").has("email") ?
                userJson.getJSONObject("kakao_account").getString("email") : null;
        String userName = userJson.has("userName") ? userJson.getString("userName") : null;
        String phone = userJson.has("phone") ? userJson.getString("phone") : null;
        String nickName = userJson.has("nickName") ? userJson.getString("nickName") : null;
        log.info("email = {}", email);

        if (email == null) {
            log.error("카카오에서 이메일 정보를 제공하지 않습니다.");
            response.sendRedirect("http://localhost:3000/login"); // 실패 시 로그인 페이지로 리다이렉트
            return;
        }

        Optional<User> optionalUser = userRepository.findByEmailAndLoginType(email, "kakao");

        if (optionalUser.isPresent()) {
            log.info("이미 등록된 사용자: {}", email);
            response.sendRedirect("http://localhost:3000/login"); // 이미 등록된 사용자는 로그인 페이지로 리다이렉트
        } else {
            User newUser = User.builder()
                    .userId(email)  // 이메일을 userId로 설정
                    .email(email)
                    .loginType("kakao")
                    .nickName(nickName)
                    .userName(userName)
                    .phone("")
                    .userType('U')
                    .password("")
                    .build();

            userRepository.save(newUser);
            log.info("회원가입 성공: {}", email);

            SocialLogin socialLogin = SocialLogin.builder()
                    .socialUserId(email)  // 이메일을 소셜 아이디로 저장
                    .userId(email)  // 이메일을 userId로 저장
                    .socialsite("kakao")
                    .loginTime(LocalDateTime.now())
                    .build();
            socialLogin.setUser(newUser);
            socialLoginRepository.save(socialLogin);

            // 회원가입 성공 후 로그인 페이지로 이동
            response.sendRedirect("http://localhost:3000/login");
        }
    }

    // 카카오 로그아웃
    @GetMapping("/kakao/logout")
    public void kakaoLogout(@RequestParam String accessToken, HttpServletResponse response) throws IOException {
        log.info("accessToken for logout = {}", accessToken);

        // 카카오 로그아웃 처리
        String logoutUrl = "https://kapi.kakao.com/v1/user/logout";
        HttpHeaders logoutHeaders = new HttpHeaders();
        logoutHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        logoutHeaders.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> logoutRequestEntity = new HttpEntity<>(logoutHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> logoutResponse = restTemplate.exchange(logoutUrl, HttpMethod.POST, logoutRequestEntity, String.class);
        log.info("logout response = {}", logoutResponse.getBody());

        // 로그아웃 성공 후 리다이렉트
        response.sendRedirect("http://localhost:3000/logout-success");
    }

//카카오연결끊기
@PostMapping("/unlink-kakao")
public ResponseEntity<?> unlinkKakaoAccount(@RequestBody Map<String, String> request) {
    String accessToken = request.get("accessToken");

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + accessToken);
    headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

    HttpEntity<String> entity = new HttpEntity<>(headers);

    RestTemplate restTemplate = new RestTemplate();
    try {
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v1/user/unlink",
                HttpMethod.POST,
                entity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok().body(Map.of("success", true, "data", response.getBody()));
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(Map.of("success", false, "error", response.getBody()));
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "error", e.getMessage()));
    }
}

    // 네이버 로그인 콜백 처리
    @GetMapping("/naver/callback")
    public void naverLogin(@RequestParam String code, HttpServletResponse response) throws IOException, JSONException {
        log.info("Received authorization code: {}", code);

        // 액세스 토큰을 요청하기 위한 URL 및 헤더 설정
        String tokenUrl = "https://nid.naver.com/oauth2.0/token";
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 본문에 필요한 파라미터 설정
        String tokenRequestBody = "grant_type=authorization_code"
                + "&client_id=" + naverClientId
                + "&client_secret=" + naverClientSecret
                + "&redirect_uri=" + naverRedirectUri
                + "&code=" + code;

        // 토큰 요청
        HttpEntity<String> tokenRequestEntity = new HttpEntity<>(tokenRequestBody, tokenHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.POST, tokenRequestEntity, String.class);
        log.info("token response = {}", tokenResponse.getBody());

        // 토큰 응답에서 액세스 토큰 추출
        JSONObject tokenJson = new JSONObject(tokenResponse.getBody());
        String accessToken = tokenJson.getString("access_token");
        log.info("accessToken = {}", accessToken);

        // 사용자 정보를 요청하기 위한 URL 및 헤더 설정
        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.set("Authorization", "Bearer " + accessToken);

        // 사용자 정보 요청
        HttpEntity<String> userInfoRequestEntity = new HttpEntity<>(userInfoHeaders);
        ResponseEntity<String> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userInfoRequestEntity, String.class);
        log.info("user info response = {}", userInfoResponse.getBody());

        // 사용자 정보 응답에서 필요한 데이터 추출
        JSONObject userJson = new JSONObject(userInfoResponse.getBody()).getJSONObject("response");
        String email = userJson.has("email") ? userJson.getString("email") : null;
        log.info("email = {}", email);

        // 이메일이 없으면 로그인 페이지로 리다이렉트
        if (email == null) {
            log.error("네이버에서 이메일 정보를 제공하지 않습니다.");
            response.sendRedirect("http://localhost:3000/login");
            return;
        }

        // 기존 사용자 확인
        Optional<User> optionalUser = userRepository.findByEmailAndLoginType(email, "naver");

        if (optionalUser.isPresent()) {
            // 기존 사용자 로그인 처리
            User user = optionalUser.get();
            user.setSnsAccessToken(accessToken);
            userRepository.save(user);

            // JWT 토큰 발급
            Long accessExpiredMs = 600000L; // 10분
            String accessTokenJwt = jwtUtil.generateToken(email, "access", accessExpiredMs);
            Long refreshExpiredMs = 7200000L; // 2시간
            String refreshTokenJwt = jwtUtil.generateToken(email, "refresh", refreshExpiredMs);

            // TokenLogin 엔티티 저장
            TokenLogin tokenLogin = TokenLogin.builder()
                    .userId(user.getUserId())
                    .accessToken(accessTokenJwt)
                    .refreshToken(refreshTokenJwt)
                    .accessCreated(LocalDateTime.now())
                    .accessExpires(LocalDateTime.now().plusMinutes(accessExpiredMs / 60000))
                    .refreshCreated(LocalDateTime.now())
                    .refreshExpires(LocalDateTime.now().plusMinutes(refreshExpiredMs / 60000))
                    .user(user)
                    .build();
            tokenLoginService.save(tokenLogin);


            // 로그인 성공 후 URL에 토큰 정보 포함하여 리다이렉트
            String redirectUrl = String.format("http://localhost:3000/login/success?access=%s&refresh=%s&userId=%s&email=%s&userName=%s&nickName=%s&phone=%s&birthday=%s&gender=%s&loginType=%s",
                    accessTokenJwt, refreshTokenJwt, user.getUserId(), user.getEmail(), user.getUserName(), user.getNickName(), user.getPhone(), user.getBirthDate(), user.getGender(), user.getLoginType());
            response.sendRedirect(redirectUrl);
            log.info("로그인 성공: {}", email);
        } else {
            // 신규 사용자 회원가입 필요
            log.info("회원가입 필요: {}", email);
            response.sendRedirect("http://localhost:3000/signup"); // 회원가입 페이지로 리다이렉트
        }
    }

    // 네이버 회원가입 콜백 처리
    @GetMapping("/naver/signup/callback")
    public void naverSignup(@RequestParam String code, HttpServletResponse response) throws IOException {
        log.info("Received authorization code: {}", code);

        // 액세스 토큰을 요청하기 위한 URL 및 헤더 설정
        String tokenUrl = "https://nid.naver.com/oauth2.0/token";
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 본문에 필요한 파라미터 설정
        String encodedRedirectUri = URLEncoder.encode(naverRedirectSignupUri, StandardCharsets.UTF_8.toString());
        String tokenRequestBody = "grant_type=authorization_code"
                + "&client_id=" + naverClientId
                + "&client_secret=" + naverClientSecret
                + "&redirect_uri=" + encodedRedirectUri
                + "&code=" + code;

        // 토큰 요청
        HttpEntity<String> tokenRequestEntity = new HttpEntity<>(tokenRequestBody, tokenHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> tokenResponse;

        try {
            // 네이버에 토큰 요청을 보내고 응답을 받음
            tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.POST, tokenRequestEntity, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("HTTP error during token request: {}", e.getStatusCode());
            response.sendRedirect("/error"); // 에러 페이지로 리다이렉트
            return;
        } catch (ResourceAccessException e) {
            log.error("Resource access error during token request: {}", e.getMessage());
            response.sendRedirect("/error"); // 에러 페이지로 리다이렉트
            return;
        }

        // 토큰 응답에서 액세스 토큰 추출
        JSONObject tokenJson = new JSONObject(tokenResponse.getBody());
        String accessToken = tokenJson.getString("access_token");
        log.info("Naver accessToken = {}", accessToken);

        // 사용자 정보를 요청하기 위한 URL 및 헤더 설정
        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.set("Authorization", "Bearer " + accessToken);

        // 사용자 정보 요청
        HttpEntity<String> userInfoRequestEntity = new HttpEntity<>(userInfoHeaders);
        ResponseEntity<String> userInfoResponse;

        try {
            // 네이버에 사용자 정보 요청을 보내고 응답을 받음
            userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userInfoRequestEntity, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("HTTP error during user info request: {}", e.getStatusCode());
            response.sendRedirect("/error"); // 에러 페이지로 리다이렉트
            return;
        } catch (ResourceAccessException e) {
            log.error("Resource access error during user info request: {}", e.getMessage());
            response.sendRedirect("/error"); // 에러 페이지로 리다이렉트
            return;
        }

        // 사용자 정보 응답에서 필요한 데이터 추출
        JSONObject userJson = new JSONObject(userInfoResponse.getBody()).getJSONObject("response");
        String email = userJson.has("email") ? userJson.getString("email") : null;
        String userName = userJson.has("userName") ? userJson.getString("userName") : null;
        String phone = userJson.has("phone") ? userJson.getString("phone") : null;
        String nickName = userJson.has("nickName") ? userJson.getString("nickName") : null;

        // 이메일이 없으면 로그인 페이지로 리다이렉트
        if (email == null) {
            response.sendRedirect("http://localhost:3000/login");
            return;
        }

        // 이미 가입된 사용자인지 확인
        Optional<User> optionalUser = userRepository.findByEmailAndLoginType(email, "naver");

        if (optionalUser.isPresent()) {
            // 이미 가입된 사용자는 로그인 페이지로 리다이렉트
            response.sendRedirect("http://localhost:3000/login");
        } else {
            // 새로운 사용자 등록
            User newUser = User.builder()
                    .userId(email)
                    .email(email)
                    .loginType("naver")
                    .userName(userName)
                    .nickName(nickName)
                    .phone("")
                    .userType('U')
                    .password("") // 비밀번호는 빈 문자열로 설정 (소셜 로그인 사용 시)
                    .build();

            userRepository.save(newUser);

            // 소셜 로그인 정보 저장
            SocialLogin socialLogin = SocialLogin.builder()
                    .socialUserId(email)
                    .userId(email)
                    .socialsite("naver")
                    .loginTime(LocalDateTime.now())
                    .build();
            socialLogin.setUser(newUser);
            socialLoginRepository.save(socialLogin);

            // 회원가입 후 로그인 페이지로 리다이렉트
            response.sendRedirect("http://localhost:3000/login");
        }
    }



    //네이버 로그아웃
    @GetMapping("/naver/logout")
    public void naverLogout(@RequestParam String accessToken, HttpServletResponse response) throws IOException {
        log.info("accessToken for logout = {}", accessToken);

        // 카카오 로그아웃 처리
        String logoutUrl = "https://kapi.kakao.com/v1/user/logout";
        HttpHeaders logoutHeaders = new HttpHeaders();
        logoutHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        logoutHeaders.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> logoutRequestEntity = new HttpEntity<>(logoutHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> logoutResponse = restTemplate.exchange(logoutUrl, HttpMethod.POST, logoutRequestEntity, String.class);
        log.info("logout response = {}", logoutResponse.getBody());

        // 로그아웃 성공 후 리다이렉트
        response.sendRedirect("http://localhost:3000/logout-success");
    }

    // 유저 정보 수정
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {

        log.info("Request user: {}", user);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUserId();
        log.info("userId={}", userId);
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사용자 세부 정보를 찾을 수 없습니다. JWT 토큰이 유효하고 사용자가 인증되었는지 확인하세요.");
        }
        if (!userDetails.getUsername().equals(user.getEmail())) {
            log.info("userDrtails.getUsername={}", userDetails.getUsername());
            log.info("userDrtails.getUsernaver={}", user.getEmail());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "인증된 사용자와 요청된 사용자 ID가 일치하지 않습니다.");
        }
        User updatedUser = userService.updateSocialUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    // 네이버 연결 끊기
    @PostMapping("/unlink-naver")
    public ResponseEntity<?> unlinkNaverAccount(@RequestBody Map<String, String> request) {
        String accessToken = request.get("accessToken");

        if (accessToken == null || accessToken.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "error", "Access token is missing"));
        }

        String unlinkUrl = "https://nid.naver.com/oauth2.0/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=delete"
                + "&client_id=" + naverClientId
                + "&client_secret=" + naverClientSecret
                + "&access_token=" + accessToken;

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        log.info("unlinkNaverAccount: {}", unlinkUrl);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    unlinkUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok().body(Map.of("success", true, "data", response.getBody()));
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(Map.of("success", false, "error", response.getBody()));
            }
        } catch (Exception e) {
            log.error("Error unlinking Naver account", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "error", e.getMessage()));
        }
    }

}