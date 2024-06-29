package com.divya.userService.UserService.dto;

import com.divya.userService.UserService.model.Role;
import com.divya.userService.UserService.model.User;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Userdto {
    String name;
    String email;
    @ManyToMany
    List<Role> roles;
    boolean isVerified;

    public static Userdto from(User user){
        Userdto userdto= Userdto.builder().email(user.getEmail()).name(user.getName()).isVerified(user.isVerified()).roles(user.getRoles()).build();
        return userdto;
    }
}
