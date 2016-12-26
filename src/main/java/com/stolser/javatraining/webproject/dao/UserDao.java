package com.stolser.javatraining.webproject.dao;

import com.stolser.javatraining.webproject.model.entity.user.User;

public interface UserDao extends GenericDao<User> {
    User findOneByUserName(String userName);
}
