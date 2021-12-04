package com.kbfina.management.entities;

import com.kbfina.management.enums.Role;

public class UserRole  {
    
    protected int id;
    protected Role role;

    public UserRole() {
    }

    public UserRole(int id, Role role) {
        this.id = id;
        this.role = role;
    }

    public Role getRole() {
        return this.role;
    }


}
