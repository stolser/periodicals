package com.stolser.javatraining.webproject.model.entity.invoice;

import com.stolser.javatraining.webproject.model.entity.user.User;

import java.util.Date;
import java.util.List;

public class Invoice {
    private long id;
    private User user;
    private double amount;
    private Status status;
    private Date creationDate;
    private List<InvoiceItem> items;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }
}
