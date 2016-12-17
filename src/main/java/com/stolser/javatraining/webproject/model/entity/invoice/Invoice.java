package com.stolser.javatraining.webproject.model.entity.invoice;

import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.user.User;

import java.io.Serializable;
import java.time.Instant;

public class Invoice implements Serializable {
    private long id;
    private User user;
    private Periodical periodical;
    private int subscriptionPeriod;
    private long totalSum;
    private Instant creationDate;
    private Instant paymentDate;
    private Status status;

    public enum Status {
        NEW, PAID;
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

    public long getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(long totalSum) {
        this.totalSum = totalSum;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Periodical getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Periodical periodical) {
        this.periodical = periodical;
    }

    public int getSubscriptionPeriod() {
        return subscriptionPeriod;
    }

    public void setSubscriptionPeriod(int subscriptionPeriod) {
        this.subscriptionPeriod = subscriptionPeriod;
    }

    @Override
    public String toString() {
        return String.format("Invoice{id=%d, user=%s, periodical=%s, subscriptionPeriod=%d, " +
                "totalSum=%s, creationDate=%s, paymentDate=%s, status=%s}",
                id, user, periodical, subscriptionPeriod, totalSum,
                creationDate, paymentDate, status);
    }
}
