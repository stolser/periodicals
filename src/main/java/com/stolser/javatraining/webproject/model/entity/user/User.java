package com.stolser.javatraining.webproject.model.entity.user;

import java.util.Date;
import java.util.Set;

public class User {
    private long id;
    private Gender gender;
    private Date birthDate;
    private String phone;
    private String email; // todo: check for uniqueness;
    private String address;
    private Set<Role> roles;

    public enum Gender {
        MALE, FEMALE;
    }

    public enum Status {
        ACTIVE, BLOCKED;
    }

    public enum Role {
        ANONYMOUS, SUBSCRIBER, ADMIN;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
