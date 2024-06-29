package com.divya.userService.UserService.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseClass{
   private String name;


}
