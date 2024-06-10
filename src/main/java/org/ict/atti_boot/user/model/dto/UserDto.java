package org.ict.atti_boot.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.user.jpa.entity.SocialLogin;
import org.ict.atti_boot.user.jpa.entity.TokenLogin;
import org.ict.atti_boot.user.jpa.entity.User;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class UserDto {

    private String userId;
    private String password;
    private String userName;
    private String nickName;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private char userType;
    private char gender;
    private String profileUrl;
    private String loginType;
    private TokenLogin tokenLogin;
    private Set<SocialLogin> socialLogins;
    private String snsAccessToken;


    public User toUserEntity(){
        return User.builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .nickName(nickName)
                .email(email)
                .phone(phone)
                .birthDate(birthDate)
                .userType(userType)
                .gender(gender)
                .profileUrl(profileUrl)
                .loginType(loginType)
                .tokenLogin(tokenLogin)
                .socialLogins(socialLogins)
                .snsAccessToken(snsAccessToken)
                .build();

    }
}
