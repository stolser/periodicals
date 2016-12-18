package com.stolser.javatraining.webproject.model.entity.subscription;

import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.user.User;

import java.time.Instant;

public class Subscription {
    private long id;
    /**
     * The user this subscription belongs to.
     */
    private User user;
    /**
     * The periodical this subscription is on.
     */
    private Periodical periodical;
    private String deliveryAddress;
    /**
     * The expiration date of this subscription. It can be prolonged by creating and paying a new invoice
     * for the same periodical.
     */
    private Instant endDate;
    /**
     * Is {@code active} when a subscription is created. Becomes {@code inactive} when
     * this subscription is expired.
     */
    private Status status;

    public enum Status {
        ACTIVE, INACTIVE;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Periodical getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Periodical periodical) {
        this.periodical = periodical;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Subscription{id=%d, user=%s, periodical=%s, deliveryAddress='%s', " +
                "endDate=%s, status=%s}", id, user, periodical, deliveryAddress, endDate, status);
    }
}
