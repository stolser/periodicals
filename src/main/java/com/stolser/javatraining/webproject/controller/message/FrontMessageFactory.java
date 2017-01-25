package com.stolser.javatraining.webproject.controller.message;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains methods for generating different types of frontend messages: 'success', 'info', 'warning',
 * 'error'. Implements the Flyweight Design Pattern.
 */
public class FrontMessageFactory {
    private static Map<String, FrontendMessage> messagesSuccess = new HashMap<>();
    private static Map<String, FrontendMessage> messagesInfo = new HashMap<>();
    private static Map<String, FrontendMessage> messagesWarning = new HashMap<>();
    private static Map<String, FrontendMessage> messagesError = new HashMap<>();

    private FrontMessageFactory() {
    }

    private static class InstanceHolder {
        private static final FrontMessageFactory INSTANCE = new FrontMessageFactory();
    }

    public static FrontMessageFactory getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public FrontendMessage getSuccess(String messageKey) {
        return getMessageFromCache(messagesSuccess, FrontendMessage.MessageType.SUCCESS, messageKey);
    }

    public FrontendMessage getInfo(String messageKey) {
        return getMessageFromCache(messagesInfo, FrontendMessage.MessageType.INFO, messageKey);
    }

    public FrontendMessage getWarning(String messageKey) {
        return getMessageFromCache(messagesWarning, FrontendMessage.MessageType.WARNING, messageKey);
    }

    public FrontendMessage getError(String messageKey) {
        return getMessageFromCache(messagesError, FrontendMessage.MessageType.ERROR, messageKey);
    }

    private FrontendMessage getMessageFromCache(Map<String, FrontendMessage> cache,
                                                FrontendMessage.MessageType messageType, String messageKey) {
        if (!cache.containsKey(messageKey)) {
            cache.put(messageKey, new FrontendMessage(messageKey, messageType));
        }

        return cache.get(messageKey);
    }
}
