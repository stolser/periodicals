package com.stolser.javatraining.webproject.model.entity.periodical;

import com.stolser.javatraining.webproject.model.entity.user.User;

import java.time.Instant;

public class Subscription {
    private long id;
    private User user;
    private Periodical periodical;
    private String deliveryAddress;
    private Instant endDate;
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
}
