package com.divya.userService.UserService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
public class Token extends BaseClass{
    private LocalDateTime expiredAt;
    private String value;
    @ManyToOne
    private User user;

}
