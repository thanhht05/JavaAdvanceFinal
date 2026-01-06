package com.project.thanh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.thanh.domain.Role;
import com.project.thanh.domain.User;
import com.project.thanh.dtos.UserDTO;
import com.project.thanh.service.RoleService;
import com.project.thanh.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String handleLogin() {
        return "user/login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());

        return "user/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute UserDTO userDTO) {

        User user = this.userService.converUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        this.userService.saveUser(user);

        return "redirect:/login";
    }

}
