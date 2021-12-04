package com.kbfina.management.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="username", unique = true)
    private String userName;

    @Column(name="password")
    private String passWord;

    @Column(name = "enable")
    private boolean enable;

    @Column(name = "role")
    private String role;

    public User() {
    }

    public User(String userName, String passWord, boolean enable, String role) {
        this.userName = userName;
        this.passWord = passWord;
        this.enable = enable;
        this.role = role;
    }

    public User(String userName, String passWord, String role) {
        this.userName = userName;
        this.passWord = passWord;
        this.role = role;
    }
    public User(String userName, String role) {
        this.userName = userName;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Role role_auth = Role.ADMINISTRATOR;
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.toLowerCase()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.passWord;
    }

    @Override
    public String getUsername() {
        
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        
        return this.enable;
    }

    @Override
    public boolean isAccountNonLocked() {
        
        return this.enable;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        
        return this.enable;
    }

    @Override
    public boolean isEnabled() {
        
        return this.enable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
}
