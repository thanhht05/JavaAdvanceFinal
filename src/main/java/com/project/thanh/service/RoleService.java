package com.project.thanh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thanh.domain.Role;
import com.project.thanh.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role getRole(String roleName) {
        return this.roleRepository.findByRoleName(roleName);
    }
}
