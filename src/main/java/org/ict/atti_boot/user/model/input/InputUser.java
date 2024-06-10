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
    private String userName;
    private LocalDate birthDate;

//    public InputUser(String userName) {
//
//        this.email = userName;
//    }
}
