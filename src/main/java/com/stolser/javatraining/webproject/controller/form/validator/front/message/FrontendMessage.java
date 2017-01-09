package com.stolser.javatraining.webproject.controller.form.validator.front.message;

import java.io.Serializable;

/**
 * Encapsulates data about a message displayed on the frontend.
 */
public final class FrontendMessage implements Serializable {
    private static final long serialVersionUID = -777L;
    private String messageKey;
    private MessageType type;

    enum MessageType {
        SUCCESS,
        INFO,
        WARNING,
        ERROR
    }

    public FrontendMessage(String messageKey, MessageType type) {
        this.messageKey = messageKey;
        this.type = type;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public MessageType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("FrontendMessage{messageKey='%s', type=%s}", messageKey, type);
    }
}
