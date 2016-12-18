package com.stolser.javatraining.webproject.controller.validator.front_message;

public final class FrontendMessage {
    private String messageKey;
    private MessageType type;

    public enum MessageType {
        SUCCESS,
        INFO,
        WARNING,
        ERROR;
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
