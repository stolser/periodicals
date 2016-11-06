package com.stolser.javatraining.block04.recordbook.model;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class Record {
    private UserName userName;
    private String comment;
    private Set<UserGroup> groups;
    private List<UserPhone> phones;
    private String email;
    private String skype;
    private UserAddress address;
    private Instant creationDate;
    private Instant lastModifDate;

    public Record(UserName userName) {
        checkNotNull(userName);
        this.userName = userName;
        this.creationDate = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record)) return false;

        Record record = (Record) o;

        return userName.equals(record.userName);
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }

    public UserName getUserName() {
        return new UserName(userName.getFirstName(), userName.getLastName(),
                userName.getExtraName(), userName.getNickname());
    }

    public void updateUserName(UserName userName) {
        checkNotNull(userName.getFirstName());
        checkNotNull(userName.getLastName());

        this.userName.setFirstName(userName.getFirstName());
        this.userName.setLastName(userName.getLastName());
        this.userName.setExtraName(userName.getExtraName());
        this.userName.setNickname(userName.getNickname());

        updateLastModifDate();
    }

    public Set<UserGroup> getGroups() {
        return groups;
    }

    public void addGroup(UserGroup userGroup) {
        checkNotNull(userGroup);
        groups.add(userGroup);

        updateLastModifDate();
    }

    public void removeGroup(UserGroup userGroup) {
        groups.remove(userGroup);

        updateLastModifDate();
    }

    public List<UserPhone> getPhones() {
        return Collections.unmodifiableList(phones);
    }

    public void addPhone(UserPhone newPhone) {
        checkNotNull(newPhone);
        phones.add(newPhone);

        updateLastModifDate();
    }

    public void removePhone(UserPhone phone) {
        phones.remove(phone);

        updateLastModifDate();
    }

    public UserAddress getAddress() {
        return new UserAddress(address.getPostalCode(), address.getLocalityType(),
                address.getLocalityName(), address.getStreetType(), address.getStreetName(),
                address.getHouseNumber(), address.getApartmentNumber());
    }

    public void updateAddress(UserAddress address) {
        checkArguments(address);

        this.address.setPostalCode(address.getPostalCode());
        this.address.setLocalityType(address.getLocalityType());
        this.address.setLocalityName(address.getLocalityName());
        this.address.setStreetType(address.getStreetType());
        this.address.setStreetName(address.getStreetName());
        this.address.setHouseNumber(address.getHouseNumber());
        this.address.setApartmentNumber(address.getApartmentNumber());

        updateLastModifDate();
    }

    private void checkArguments(UserAddress address) {
        checkNotNull(address);
        checkNotNull(address.getLocalityName());
        checkNotNull(address.getStreetType());
        checkNotNull(address.getStreetName());
        checkNotNull(address.getHouseNumber());
        checkNotNull(address.getApartmentNumber());
    }

    public String getComment() {
        return comment;
    }

    public String getEmail() {
        return email;
    }

    public String getSkype() {
        return skype;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Instant getLastModifDate() {
        return lastModifDate;
    }

    public void setComment(String comment) {
        this.comment = comment;

        updateLastModifDate();
    }

    public void setEmail(String email) {
        this.email = email;

        updateLastModifDate();
    }

    public void setSkype(String skype) {
        this.skype = skype;

        updateLastModifDate();
    }

    private void updateLastModifDate() {
        this.lastModifDate = Instant.now();
    }
}
