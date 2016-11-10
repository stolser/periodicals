package com.stolser.javatraining.block04.recordbook.model.user;

import static com.google.common.base.Preconditions.*;

/**
 * Represents an abstraction of a user address.
 */
public class UserAddress implements Cloneable {
    private String postCode;
    private LocalityType localityType;
    private String localityName;
    private StreetType streetType;
    private String streetName;
    private String houseNumber;
    private String apartmentNumber;

    public enum LocalityType {
        CITY, TOWN, VILLAGE, SETTLEMENT, RANCH;
    }

    public enum StreetType {
        STREET("St.", "Street"),
        ROAD("Rd.", "Road"),
        AVENUE("Av.", "Avenue"),
        DRIVE("Dr.", "Drive"),
        SQUARE("Sq.", "Square");

        private String shortName;
        private String fullName;

        StreetType(String shortName, String fullName) {
            this.shortName = shortName;
            this.fullName = fullName;
        }

        public String getShortName() {
            return shortName;
        }

        public String getFullName() {
            return fullName;
        }
    }

    public UserAddress(String postCode, LocalityType localityType, String localityName,
                       StreetType streetType, String streetName, String houseNumber,
                       String apartmentNumber) {
        checkArguments(localityName, streetType, streetName, houseNumber, apartmentNumber);

        this.postCode = postCode;
        this.localityType = localityType;
        this.localityName = localityName;
        this.streetType = streetType;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
    }

    private void checkArguments(String localityName, StreetType streetType, String streetName,
                                String houseNumber, String apartmentNumber) {

        checkNotNull(localityName);
        checkNotNull(streetType);
        checkNotNull(streetName);
        checkNotNull(houseNumber);
        checkNotNull(apartmentNumber);
    }

    /**
     * @return a formatted representation of this address.
     * An example: '24 Lomonosova St., Apt 104B, Kiev 03022'
     */
    public String getFullAddress() {
        return String.format("%s %s %s, Apt %s, %s %s", houseNumber, streetName,
                streetType.getShortName(), apartmentNumber, localityName, postCode);
    }

    @Override
    public String toString() {
        return String.format("UserAddress{postCode='%s', localityType=%s, localityName='%s', " +
                "streetType=%s, streetName='%s', houseNumber='%s', apartmentNumber='%s'}",
                postCode, localityType, localityName,
                streetType, streetName, houseNumber, apartmentNumber);
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public LocalityType getLocalityType() {
        return localityType;
    }

    public void setLocalityType(LocalityType localityType) {
        this.localityType = localityType;
    }

    public String getLocalityName() {
        return localityName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    public StreetType getStreetType() {
        return streetType;
    }

    public void setStreetType(StreetType streetType) {
        this.streetType = streetType;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    @Override
    public UserAddress clone() {
        UserAddress clone;

        try {
            clone = (UserAddress) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException();
        }

        return clone;
    }
}
