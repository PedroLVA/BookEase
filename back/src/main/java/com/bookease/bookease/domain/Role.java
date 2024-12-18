package com.bookease.bookease.domain;

public enum Role {

    ADMIN("admin"),
    USER("user"),
    ORGANIZER("organizer");

    private String role;

    Role(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
