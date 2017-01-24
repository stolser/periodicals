package com.stolser.javatraining.webproject.model.entity.user;

import static com.google.common.base.Preconditions.checkNotNull;

public class Credential {
    private long id;
    private String userName;
    private String passwordHash;
    private long userId;

    public static class Builder {
        private Credential credential;

        public Builder() {
            credential = new Credential();
        }

        public Builder setId(long id) {
            credential.setId(id);
            return this;
        }

        public Builder setUserName(String userName) {
            credential.setUserName(userName);
            return this;
        }

        public Builder setPasswordHash(String passwordHash) {
            credential.setPasswordHash(passwordHash);
            return this;
        }

        public Builder setUserId(long userId) {
            credential.setUserId(userId);
            return this;
        }

        public Credential build() {
            return credential;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        checkNotNull(userName);
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        checkNotNull(passwordHash);
        this.passwordHash = passwordHash;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Credential that = (Credential) o;

        if (id != that.id) {
            return false;
        }
        return userName != null ? userName.equals(that.userName) : that.userName == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }
}
