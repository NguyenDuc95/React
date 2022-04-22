package com.kbfina.management.enums;

public enum Role {
    ADMINISTRATOR, CLIENT;

    public String authority() {
        return this.name().toLowerCase();
    }
}
