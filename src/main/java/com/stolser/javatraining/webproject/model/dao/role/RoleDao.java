package com.stolser.javatraining.webproject.model.dao.role;

import java.util.Set;

public interface RoleDao {
    /**
     * @return all roles that a user with the specified username has
     */
    Set<String> findRolesByUserName(String userName);
}
