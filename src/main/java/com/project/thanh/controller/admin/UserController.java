package com.project.thanh.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.thanh.domain.Role;
import com.project.thanh.domain.User;
import com.project.thanh.service.RoleService;
import com.project.thanh.service.UserService;

@Controller
public class UserController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/initUser")
    public String initiUser() {
        Role r = new Role();
        r.setRoleName("ADMIN");

        r = roleService.createRole(r);

        User u = new User();
        u.setEmail("admin@gmail.com");
        u.setFullName("ADMIN");
        u.setPhone("0898173004");
        String pass = "2005";
        u.setPassword(passwordEncoder.encode(pass));
        u.setRole(r);

        this.userService.saveUser(u);

        return "create user successlly";
    }

}
