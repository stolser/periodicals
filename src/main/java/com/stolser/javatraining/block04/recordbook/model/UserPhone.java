package com.stolser.javatraining.block04.recordbook.model;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Immutable class that can represent a user phone of any type (mobile and not).
 */
public final class UserPhone {
    private static final String PREFIX = "+38 0";
    private String code;
    private String number;
    private boolean isMobile;
    private UserPhoneType type;

    public enum UserPhoneType {
        HOME, WORK;
    }

    public UserPhone(String code, String number, boolean isMobile, UserPhoneType type) {
        checkArguments(code, number, type);

        this.code = code;
        this.number = number;
        this.isMobile = isMobile;
        this.type = type;
    }

    private void checkArguments(String code, String number, UserPhoneType type) {
        checkNotNull(code);
        checkNotNull(number);
        checkNotNull(type);
    }

    /**
     * @return a full textual representation of this phone in the format '+380 XXX XX XX'.
     */
    public String getFullNumber() {
        return String.format("%s%s %s", PREFIX, code, number);
    }

    @Override
    public String toString() {
        return String.format("UserPhone{code='%s', number='%s', isMobile=%s, type=%s}",
                code, number, isMobile, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPhone)) return false;

        UserPhone userPhone = (UserPhone) o;

        if (!code.equals(userPhone.code)) return false;
        return number.equals(userPhone.number);

    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + number.hashCode();
        return result;
    }

    public String getCode() {
        return code;
    }

    public String getNumber() {
        return number;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public UserPhoneType getType() {
        return type;
    }
}
