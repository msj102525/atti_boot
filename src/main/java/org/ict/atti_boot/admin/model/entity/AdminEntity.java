package org.ict.atti_boot.admin.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS") //수정하기
public class AdminEntity {
    //**** 밑에꺼 삭제하기 *****
    @Id
    @Column(name = "USER_ID", length = 50, nullable = false) // 컬럼명과 길이 등을 맞추어야 함
    private String userId;

    @Column(name = "USER_NAME", length = 50, nullable = false)
    private String userName;

    @Column(name = "NICK_NAME", length = 50)
    private String nickName;

    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    // 기타 필요한 필드는 여기에 추가

}
