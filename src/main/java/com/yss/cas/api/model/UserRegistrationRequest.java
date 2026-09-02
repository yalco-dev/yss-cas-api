package com.yss.cas.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
    private String email;
    private String password;
    private String confirmedPassword;
    private String phoneNumber;
    private String firstName;
    private String lastName;
}
