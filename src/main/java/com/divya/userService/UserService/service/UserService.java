package com.divya.userService.UserService.service;

import com.divya.userService.UserService.dto.LogoutRequestdto;
import com.divya.userService.UserService.dto.SignupRequestdto;
import com.divya.userService.UserService.exception.InvalidPasswordException;
import com.divya.userService.UserService.exception.InvalidTokenException;
import com.divya.userService.UserService.exception.UserNotFoundException;
import com.divya.userService.UserService.model.Role;
import com.divya.userService.UserService.model.Token;
import com.divya.userService.UserService.model.User;
import com.divya.userService.UserService.repository.RoleRepository;
import com.divya.userService.UserService.repository.TokenRepository;
import com.divya.userService.UserService.repository.UserRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private RoleRepository roleRepository;
//TODO: Send email to kafka
    public User signup(SignupRequestdto req) {
        Optional<User> optionalUser= userRepository.findByEmail(req.getEmail());
        if(optionalUser.isPresent()){
            //user is already present in the DB, so no need to signup
            return optionalUser.get();
        }
        User user = User.builder().email(req.getEmail())
                .name(req.getName()).hashedPassword(bCryptPasswordEncoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .email(req.getEmail())
                .build();
        user.setVerified(false);
       Role role= new Role("admin");
        Role role2 = roleRepository.findRoleByName(role.getName());
       if(role2==null){
           role2=roleRepository.save(role);
        };
        user.setRoles(List.of(role2));
        user.setStatus("active");
        return userRepository.save(user);
    }

    public Token login(String email, String password) throws UserNotFoundException, InvalidPasswordException {
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new UserNotFoundException("UserNot exist, signup");
        } else {
            User user = optional.get();
            if (bCryptPasswordEncoder.matches( password,user.getHashedPassword())) {
                Token token = generateToken(user);
                return tokenRepository.save(token);
            } else {
                throw new InvalidPasswordException("incorrect password");
            }
        }


    }

    public Token generateToken(User user) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expiryTime = currentTime.plusDays(30);
        Token token = new Token();
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setExpiredAt(expiryTime);
        token.setUser(user);
        return token;
    }

    public User validateToken(String token) throws InvalidTokenException {
        Optional<Token> optionalToken= tokenRepository.findByValueAndDeleted(token,false);
        if(optionalToken.isEmpty()){
            throw new InvalidTokenException("Invaid token");
        }
        return optionalToken.get().getUser();
    }

    public Token logout(LogoutRequestdto logoutRequestdto) throws InvalidTokenException{
        Optional<Token> optional = tokenRepository.findByValueAndDeleted(logoutRequestdto.getToken(), false);
        if(optional.isPresent())
            return optional.get();
        else{
            throw new InvalidTokenException("Invalid token");
        }
    }
}

//public