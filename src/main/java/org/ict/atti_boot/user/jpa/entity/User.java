package org.ict.atti_boot.user.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "USERS")
@Check(constraints = "USER_TYPE IN ('U', 'A', 'D')")
@Entity
public class User{

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

    @Column(name = "LOGIN_TYPE")
    private String loginType;

    @Column(name = "SNS_ACCESS_TOKEN", nullable = true)
    private String snsAccessToken;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private Set<SocialLogin> socialLogin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SocialLogin> socialLogin;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<RefreshToken> refreshToken;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private TokenLogin tokenLogin;

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
    public User(String userId, String password, String userName) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
    }

    public void setUserType(String userType) {
        if (userType != null && !userType.isEmpty()) {
            this.userType = userType.charAt(0);
        } else {
            this.userType = 'U';  // 기본값 설정
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
