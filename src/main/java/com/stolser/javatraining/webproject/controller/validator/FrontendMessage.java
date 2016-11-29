package com.stolser.javatraining.webproject.controller.validator;

public class FrontendMessage {
    private String fieldName;
    private String messageKey;
    private MessageType type;

    public enum MessageType {
        SUCCESS,
        INFO,
        WARNING,
        ERROR;
    }

    public FrontendMessage(String fieldName, String messageKey, MessageType type) {
        this.fieldName = fieldName;
        this.messageKey = messageKey;
        this.type = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
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
        return String.format("FrontendMessage{fieldName='%s', messageKey='%s', type=%s}", fieldName, messageKey, type);
    }
}
