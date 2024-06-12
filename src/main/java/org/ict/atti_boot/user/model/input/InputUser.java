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
    private Long userId;
    private String userName;
    private LocalDate birthDate;

    public InputUser(Long username) {
        this.userId = username;
    }

    public InputUser(String username) {
    }
}
