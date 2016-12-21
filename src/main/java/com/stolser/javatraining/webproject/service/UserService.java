package com.stolser.javatraining.webproject.service;

import com.stolser.javatraining.webproject.model.entity.user.Credential;
import com.stolser.javatraining.webproject.model.entity.user.User;

import java.util.List;

public interface UserService {
    User findOneById(long id);

    Credential findOneCredentialByUserName(String userName);

    User findOneUserByUserName(String userName);

    /**
     * @return all the users in the database
     */
    List<User> findAll();
    void createNewUser(User user, Credential credential, String userRole);
}
