package com.tungnx.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationLoginUserDto {
    private int id;
    private String username;
    private String password;
    private String roleName;
    private String avatar;
}
