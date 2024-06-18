package org.ict.atti_boot.user.model.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputUser {
    private String email, password;
    private String userId;
    private String nickName;
    private Character gender;
    private String userName;
    private LocalDate birthDate;

    public InputUser(String username) {
        this.userId = username;
    }
}
