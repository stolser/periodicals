package com.stolser.javatraining.webproject.model.dao.credential;

import com.stolser.javatraining.webproject.model.entity.user.Credential;

public interface CredentialDao {
    Credential findCredentialByUserName(String userName);
}
