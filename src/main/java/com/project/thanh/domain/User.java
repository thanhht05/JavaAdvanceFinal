package com.project.thanh.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    @Column(columnDefinition = "NVARCHAR(200)")
    private String fullName;

    private String phone;
    @Column(columnDefinition = "NVARCHAR(200)")
    private String address;
    private String password;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;

}
