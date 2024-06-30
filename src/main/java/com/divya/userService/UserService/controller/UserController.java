package com.divya.userService.UserService.controller;

import com.divya.userService.UserService.dto.LoginRequestdto;
import com.divya.userService.UserService.dto.LogoutRequestdto;
import com.divya.userService.UserService.dto.SignupRequestdto;
import com.divya.userService.UserService.dto.Userdto;
import com.divya.userService.UserService.exception.InvalidTokenException;
import com.divya.userService.UserService.model.Token;
import com.divya.userService.UserService.model.User;
import com.divya.userService.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")//http://localhost:3030/users/signup
    public Userdto Signup(@RequestBody SignupRequestdto req) {
        User user = userService.signup(req);
        Userdto dto = Userdto.from(user);
        return dto;

    }

    @PostMapping("/login")//http://localhost:3030/users/login
    public Token login(@RequestBody LoginRequestdto req) {
        try {
            return userService.login(req.getEmail(), req.getPassword());
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/validate/{token}")
    public ResponseEntity<Userdto> validateToken(@PathVariable String token) throws InvalidTokenException {
        Userdto userdto= Userdto.from(userService.validateToken(token));
        return new ResponseEntity<Userdto>(userdto,HttpStatus.OK);

    }
@PostMapping("/logout")//http://localhost:3030/users/logout
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestdto logoutRequestdto) throws InvalidTokenException {
        try {
             userService.logout(logoutRequestdto);
             ResponseEntity<Void> resp= new ResponseEntity<>(HttpStatus.OK);
             return resp;
        }

        catch(Exception e){
            System.out.println("Something went wrong");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
