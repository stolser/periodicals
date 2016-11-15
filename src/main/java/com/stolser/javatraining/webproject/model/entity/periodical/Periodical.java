package com.stolser.javatraining.webproject.model.entity.periodical;

import java.util.List;

public class Periodical {
    private int id;
    private String name;
    private Category category;
    private Publisher publisher;
    private String generalDescription;
    private String publishingDescription;
    private List<NumberDiscount> discounts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }

    public String getPublishingDescription() {
        return publishingDescription;
    }

    public void setPublishingDescription(String publishingDescription) {
        this.publishingDescription = publishingDescription;
    }

    public List<NumberDiscount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<NumberDiscount> discounts) {
        this.discounts = discounts;
    }
}
