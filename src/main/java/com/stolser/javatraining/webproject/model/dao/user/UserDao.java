package com.stolser.javatraining.webproject.model.dao.user;

import com.stolser.javatraining.webproject.model.dao.GeneralDao;
import com.stolser.javatraining.webproject.model.entity.user.User;

public interface UserDao extends GeneralDao<User> {
    User findUserByUserName(String userName);
}
