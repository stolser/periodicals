package com.stolser.javatraining.webproject.service;

import com.stolser.javatraining.webproject.model.entity.user.Credential;
import com.stolser.javatraining.webproject.model.entity.user.User;

import java.util.List;

public interface UserService {
    User findOneById(long id);

    Credential findOneCredentialByUserName(String userName);

    User findOneUserByUserName(String userName);

    List<User> findAll();

    boolean createNewUser(User user, Credential credential, User.Role userRole);

    boolean emailExistsInDb(String email);
}
