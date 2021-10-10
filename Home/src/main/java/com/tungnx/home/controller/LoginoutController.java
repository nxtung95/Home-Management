package com.tungnx.home.controller;

import com.tungnx.home.dto.UserLoginRequestDto;
import com.tungnx.home.service.impl.UserDetailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/app")
public class LoginoutController {
    private static final Logger log = LoggerFactory.getLogger(LoginoutController.class);

    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("userLoginDto", new UserLoginRequestDto());
        return "login";
    }

//    @GetMapping(value = "/logout")
//    public void logout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null && session.getAttribute("userId") != null) {
//            log.info("User logout system, remove session attribute userId = " + session.getAttribute("userId"));
//            session.removeAttribute("userId");
//        }
//    }

    @RequestMapping(value = "/login403")
    public String errorLogin403(HttpServletRequest request) {
        log.info("Access denided from: " + request.getRequestURI());
        return "error/403_login";
    }
}
