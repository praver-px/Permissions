package com.praver.springboot.controller.dto;


import com.praver.springboot.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Integer id;
    private String avatar;
    private String username;
    private String nickname;
    private String password;
    private String token;
    private String role;
    private List<Menu> Menus;

}
