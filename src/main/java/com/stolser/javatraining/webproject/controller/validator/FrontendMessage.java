package com.stolser.javatraining.webproject.controller.validator;

public class FrontendMessage {
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

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("FrontendMessage{messageKey='%s', type=%s}", messageKey, type);
    }
}
