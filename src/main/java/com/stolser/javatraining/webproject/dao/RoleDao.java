package com.stolser.javatraining.webproject.dao;

import java.util.Set;

public interface RoleDao {
    /**
     * Retrieves all the roles that a user with the specified username has.
     */
    Set<String> findRolesByUserName(String userName);

    void addRole(long userId, String roleName);
}
