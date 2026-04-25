package com.project.thanh.controller.admin;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.thanh.domain.Role;
import com.project.thanh.domain.Room;
import com.project.thanh.domain.RoomType;
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

    @GetMapping("/admin/users")
    public String handleGetUserPage(Model model, @RequestParam(defaultValue = "1") int page) {
        int size = 6;
        Page<User> userPages = this.userService.getRommPage(page, size);
        model.addAttribute("curPage", page);
        model.addAttribute("users", userPages.getContent());
        model.addAttribute("totalPages", userPages.getTotalPages());
        return "admin/user/show";
    }

    @GetMapping("/admin/create-user")
    public String getCreateUserPage(Model model) {

        model.addAttribute("user", new User());

        return "admin/user/create";
    }

    @PostMapping("/admin/create-user")
    public String handleCreateUser(@ModelAttribute User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userService.saveUser(user);

        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String getUpdateUserPage(@PathVariable Long id, Model model) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);

        return "admin/user/update";
    }

    @PostMapping("/admin/users/update")
    public String handleUpdateRoom(@ModelAttribute("user") User user) {

        User updateUser = this.userService.getUserById(user.getId());

        updateUser.setFullName(user.getFullName());
        updateUser.setPhone(user.getPhone());
        updateUser.setAddress(user.getAddress());

        this.userService.saveUser(updateUser);

        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String handleGetDeleteUserPage(@PathVariable Long id, Model model) {
        User user = this.userService.getUserById(id);

        model.addAttribute("user", user);
        return "admin/user/delete";
    }

    @PostMapping("/admin/users/delete/{id}")
    public String handleDeleteUser(@PathVariable Long id) {

        return "redirect:/admin/users";
    }
}
