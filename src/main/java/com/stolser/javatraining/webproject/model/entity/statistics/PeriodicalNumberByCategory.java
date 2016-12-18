package com.stolser.javatraining.webproject.model.entity.statistics;

import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;

/**
 * Represents quantitative statistics on existing periodicals divided by status.
 */
public class PeriodicalNumberByCategory {
    private PeriodicalCategory category;
    private int active;
    private int inActive;
    private int discarded;

    public PeriodicalNumberByCategory(Builder builder) {
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

        public PeriodicalNumberByCategory build() {
            return new PeriodicalNumberByCategory(this);
        }
    }

}
