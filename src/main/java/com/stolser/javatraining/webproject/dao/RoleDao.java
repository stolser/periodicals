package com.stolser.javatraining.webproject.dao;

import com.stolser.javatraining.webproject.model.entity.user.User;

import java.util.Set;

public interface RoleDao {
    /**
     * Retrieves all the roles that a user with the specified username has.
     */
    Set<User.Role> findRolesByUserName(String userName);

    void addRole(long userId, User.Role role);
}
