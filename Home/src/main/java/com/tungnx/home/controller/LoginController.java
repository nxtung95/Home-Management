package com.tungnx.home.controller;

import com.tungnx.home.dto.UserLoginRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/app")
public class LoginController {

    @GetMapping(value = "/login")
    public String index(Model model) {
        model.addAttribute("userLoginDto", new UserLoginRequestDto());
        return "login";
    }

    @RequestMapping(value = "/login403")
    public String errorLogin403() {
        return "403_login";
    }
}
