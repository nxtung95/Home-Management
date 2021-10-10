package com.tungnx.home.repository;

import com.tungnx.home.dto.AuthenticationLoginUserDto;
import com.tungnx.home.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT new com.tungnx.home.dto.AuthenticationLoginUserDto(u.id, u.username, u.password, r.name, u.avatar) FROM User u JOIN u.roles r WHERE u.username = :username")
    List<AuthenticationLoginUserDto> findByUsername(@Param("username") String username);
}
