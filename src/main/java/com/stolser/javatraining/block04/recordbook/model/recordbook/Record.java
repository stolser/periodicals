package com.stolser.javatraining.block04.recordbook.model.recordbook;

import com.stolser.javatraining.block04.recordbook.model.user.UserAddress;
import com.stolser.javatraining.block04.recordbook.model.user.UserGroup;
import com.stolser.javatraining.block04.recordbook.model.user.UserName;
import com.stolser.javatraining.block04.recordbook.model.user.UserPhone;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents one record in a RecordBook. Contains all the info about a user.<br />
 * Records are stored in no particular order. The uniqueness is determined by a user name,<br />
 * so there are no two records in the same RecordBook which user name are equal.
 * The {@link Record#clone()} is implemented using deep copying.
 */
public class Record implements Cloneable {
    private UserName userName;
    private String comment;
    private Set<UserGroup> groups;
    private Set<UserPhone> phones;
    private String email;
    private String skype;
    private UserAddress address;
    private Instant creationDate;
    private Instant lastModifDate;

    public Record(UserName userName) {
        checkNotNull(userName);
        this.userName = userName;
        this.groups = new HashSet<>();
        this.phones = new HashSet<>();
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
        return Collections.unmodifiableSet(groups);
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

    public Set<UserPhone> getPhones() {
        return Collections.unmodifiableSet(phones);
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

    /**
     * @return a clone of the UserAddress
     */
    public UserAddress getAddress() {
        return address.clone();
    }

    /**
     * @param address represents values that will used to update UserAddress.
     *      Before creating a clone of a passes address it checks for not nullity of
     *      the following field: localityName, streetType, streetName,
     *      houseNumber, apartmentNumber. If any of the aforementioned fields is null
     *      this method throws NPE.
     */
    public void updateAddress(UserAddress address) {
        checkArguments(address);
        this.address = address.clone();

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

    /**
     * This setter will set the creation date only once and only if it was {@code null} before.
     * So, you cannot change the creation date using this method.
     */
    public void setCreationDate(Instant creationDate) {
        if (this.creationDate == null) {
            this.creationDate = creationDate;
        }
    }

    private void updateLastModifDate() {
        this.lastModifDate = Instant.now();
    }

    /**
     * @return a new instance of Record. Implements deep copying of all fields.
     */
    @Override
    public Record clone() {
        Record clone;

        try {
            clone = (Record) super.clone();
            clone.userName = clone.getUserName().clone();
            clone.groups = new HashSet<>(groups);
            clone.phones = new HashSet<>(phones);
            clone.address = clone.getAddress().clone();

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return clone;
    }
}