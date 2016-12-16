package com.stolser.javatraining.webproject.model.entity.periodical.statistics;

import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;

public class NumberByCategory {
    private PeriodicalCategory category;
    private int active;
    private int inActive;
    private int discarded;

    public NumberByCategory(Builder builder) {
        this.category = builder.category;
        this.active = builder.active;
        this.inActive = builder.inActive;
        this.discarded = builder.discarded;
    }

    public static Builder newBuilder(PeriodicalCategory category) {
        return new Builder(category);
    }

    public PeriodicalCategory getCategory() {
        return category;
    }

    public int getActive() {
        return active;
    }

    public int getInActive() {
        return inActive;
    }

    public int getDiscarded() {
        return discarded;
    }

    public static class Builder {
        private PeriodicalCategory category;
        private int active;
        private int inActive;
        private int discarded;

        private Builder(PeriodicalCategory category) {
            this.category = category;
        }

        public Builder setActive(int active) {
            this.active = active;
            return this;
        }

        public Builder setInActive(int inActive) {
            this.inActive = inActive;
            return this;
        }

        public Builder setDiscarded(int discarded) {
            this.discarded = discarded;
            return this;
        }

        public NumberByCategory build() {
            return new NumberByCategory(this);
        }
    }

}
