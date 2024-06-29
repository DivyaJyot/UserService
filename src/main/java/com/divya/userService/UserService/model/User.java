package com.divya.userService.UserService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
//TODO: test eager fetch
public class User extends BaseClass{
    String name;
    String hashedPassword;
    String email;
    String phone;
    @ManyToMany
    List<Role> roles;

    boolean verified;
    String status;
}
