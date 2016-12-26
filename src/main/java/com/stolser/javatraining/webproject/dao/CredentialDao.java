package com.stolser.javatraining.webproject.dao;

import com.stolser.javatraining.webproject.model.entity.user.Credential;

public interface CredentialDao {
    Credential findCredentialByUserName(String userName);
    void createNew(Credential credential);
}
