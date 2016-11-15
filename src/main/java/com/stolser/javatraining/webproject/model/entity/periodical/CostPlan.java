package com.stolser.javatraining.webproject.model.entity.periodical;

public class CostPlan {
    private int id;
    private Periodical periodical;
    private PlanType type;
    private double cost;

    public enum PlanType {
        ONE_ISSUE("1 (next) issue"),
        ONE_WEEK("1 week prepaid"),
        ONE_MONTH("1 month prepaid"),
        SIX_MONTH("6 months prepaid"),
        ONE_YEAR("one solid year prepaid");

        private String description;

        PlanType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Periodical getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Periodical periodical) {
        this.periodical = periodical;
    }

    public PlanType getType() {
        return type;
    }

    public void setType(PlanType type) {
        this.type = type;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
