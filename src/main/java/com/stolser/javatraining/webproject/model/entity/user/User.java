package com.stolser.javatraining.webproject.model.entity.user;

import java.util.Date;
import java.util.Set;

public class User {
    private long id;
    private String userName;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
    private String address;
    private Status status;
    private Set<String> roles;

    public enum Status {
        ACTIVE, BLOCKED;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, userName='%s', firstName='%s', lastName='%s', " +
                "birthDate=%s, email='%s', address='%s', status=%s, roles=%s}",
                id, userName, firstName, lastName,
                birthDate, email, address, status, roles);
    }
}
