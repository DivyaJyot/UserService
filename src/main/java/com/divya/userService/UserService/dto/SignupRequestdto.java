package com.divya.userService.UserService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class SignupRequestdto {
    String name;
    String email;
    String password;
    String phone;
}
