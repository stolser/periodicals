package com.stolser.javatraining.webproject.model.entity.periodical;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public enum PeriodicalCategory {
    NEWS(MSG_KEY_CATEGORY_NEWS),
    NATURE(MSG_KEY_CATEGORY_NATURE),
    FITNESS(MSG_KEY_CATEGORY_FITNESS),
    BUSINESS(MSG_KEY_CATEGORY_BUSINESS),
    SPORTS(MSG_KEY_CATEGORY_SPORTS),
    SCIENCE_AND_ENGINEERING(MSG_KEY_CATEGORY_SCIENCE_AND_ENGINEERING),
    TRAVELING(MSG_KEY_CATEGORY_TRAVELING);

    private String messageKey;

    PeriodicalCategory(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
