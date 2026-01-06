package com.project.thanh.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
import org.springframework.stereotype.Service;

import com.project.thanh.domain.Role;
import com.project.thanh.domain.User;
import com.project.thanh.dtos.UserDTO;
import com.project.thanh.repository.RoleRepository;
import com.project.thanh.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User converUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setPhone(userDTO.getPhone());

        Role role = this.roleRepository.findByRoleName("USER");
        user.setRole(role);

        return user;
    }
}
