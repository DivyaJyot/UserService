package com.divya.userService.UserService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestdto {
    private String email;
    private String password;
}
