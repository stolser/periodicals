package com.stolser.javatraining.webproject.model.dao.login;

import com.stolser.javatraining.webproject.model.entity.user.Login;

public interface LoginDao {
    Login findLoginByUserName(String userName);
}
