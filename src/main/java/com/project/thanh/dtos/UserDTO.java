package com.project.thanh.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String email;
    private String phone;
    private String fullName;
    private String password;
    private String confirmPassword;
}
