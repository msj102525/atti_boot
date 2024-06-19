package org.ict.atti_boot.user.model.output;


import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.admin.repository.SuspensionRepository;
import org.ict.atti_boot.user.jpa.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
// Spring Security의 UserDetails 인터페이스를 구현한 CustomUserDetails 클래스입니다.
public class CustomUserDetails implements UserDetails {

    private final User user; // 사용자 정보를 담고 있는 User 엔티티의 인스턴스입니다.
    private final SuspensionRepository suspensionRepository;
    private final boolean isAdmin; // 추가된 변수

    // 클래스 생성자에서 User 엔티티의 인스턴스를 받아 멤버 변수에 할당합니다.
    public CustomUserDetails(User user, boolean isAdmin, SuspensionRepository suspensionRepository) {
        this.user = user;
        this.suspensionRepository = suspensionRepository;
        this.isAdmin = isAdmin; // 생성자에서 설정
    }

    // 사용자의 권한 목록을 반환하는 메서드입니다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 사용자의 isAdmin 값에 따라 ROLE_ADMIN 또는 ROLE_USER 권한을 부여합니다.
        if (this.isAdmin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            log.info("SimpleGrantedAuthority:{}", authorities);
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorities;
    }

    // 사용자의 비밀번호를 반환합니다.
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 사용자의 이메일(사용자명)을 반환합니다.
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public String getUserId(){return user.getUserId();}

    // 계정이 만료되었는지를 반환합니다. 여기서는 만료되지 않았다고 가정합니다.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않은지를 반환합니다.
    @Override
    public boolean isAccountNonLocked() {
        //return !this.user.getIsDelete(); // isDelete가 false이면 계정이 잠겨있지 않은 것으로 간주합니다.
        boolean isLocked = suspensionRepository.findByUserIdAndSuspensionStatus(user.getUserId(), "unactive").isPresent();
        return !isLocked;
    }

    // 사용자의 크리덴셜(비밀번호 등)이 만료되지 않았는지를 반환합니다.
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 여기서는 크리덴셜이 만료되지 않았다고 가정합니다.
    }

    // 사용자 계정이 활성화(사용 가능) 상태인지를 반환합니다.
//    @Override
//    public boolean isEnabled() {
//        return !this.user.getIsActivated(); // isActivated가 false이면 활성화 상태입니다.
//    }
}
