package org.ict.atti_boot.user.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;
import org.ict.atti_boot.security.model.entity.RefreshToken;
import org.ict.atti_boot.user.model.dto.UserDto;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "USERS")
@Check(constraints = "USER_TYPE IN ('U', 'A', 'D')")
@Entity
public class User {

    @Id
    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "USER_TYPE")
    private Character userType;

    @Column(name = "GENDER")
    private Character gender;

    @Column(name = "PROFILE_URL")
    private String profileUrl;

    @Column(name = "login_type")
    private String loginType;

    @Column(name = "sns_access_token", nullable = true)
    private String snsAccessToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private TokenLogin tokenLogin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<SocialLogin> socialLogins;

/*    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RefreshToken> refreshTokens;*/

    public User(String userName) {
        this.userName = userName;
    }


    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public UserDto toUserDto() {
        return UserDto.builder()
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
                .build();
    }
}
