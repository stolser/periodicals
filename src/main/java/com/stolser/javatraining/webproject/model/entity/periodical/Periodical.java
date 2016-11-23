package com.stolser.javatraining.webproject.model.entity.periodical;

public class Periodical {
    private long id;
    private String name;
    private String category;
    private String publisher;
    private String description;
    private double oneMonthCost;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getOneMonthCost() {
        return oneMonthCost;
    }

    public void setOneMonthCost(double oneMonthCost) {
        this.oneMonthCost = oneMonthCost;
    }
}
