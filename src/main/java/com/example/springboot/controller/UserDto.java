package com.example.springboot.controller;

import com.example.springboot.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String password;

    private String nickname;
    private String role;
    private List<Menu> menus;
}
