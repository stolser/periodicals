package com.stolser.javatraining.webproject.model.dao.user;

import com.stolser.javatraining.webproject.model.dao.GenericDao;
import com.stolser.javatraining.webproject.model.entity.user.User;

public interface UserDao extends GenericDao<User> {
    User findOneByUserName(String userName);
}
