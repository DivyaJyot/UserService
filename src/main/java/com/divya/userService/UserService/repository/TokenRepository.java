package com.divya.userService.UserService.repository;

import com.divya.userService.UserService.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    @Override
    <S extends Token> S save(S entity);

    @Override
    Optional<Token> findById(Long aLong);

    //select * form tokens where value = <> and is_deleted = false.
    Optional<Token> findByValueAndDeleted(String value, boolean isDeleted);
}
