package com.example.lab2.jms;

import org.springframework.stereotype.Component;


@Component
public class EventListenerFactorImpl implements EventListenerFactory {
    @Override
    public EventListener createEmailLoggerListener() {
        return new EmailLoggerListener();
    }

    @Override
    public EventListener createEventLoggerListener() {
        return new EventLoggerListener();
    }
}
