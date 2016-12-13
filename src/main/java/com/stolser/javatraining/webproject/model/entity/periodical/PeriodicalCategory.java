package com.stolser.javatraining.webproject.model.entity.periodical;

public enum PeriodicalCategory {
    NEWS("category.news"),
    NATURE("category.nature"),
    FITNESS("category.fitness"),
    BUSINESS("category.business"),
    SPORTS("category.sports"),
    SCIENCE_AND_ENGINEERING("category.scienceAndEngineering"),
    TRAVELING("category.traveling");

    private String messageKey;

    PeriodicalCategory(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
