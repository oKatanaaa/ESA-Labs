package com.example.lab2.jms;


import com.example.lab2.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;



@Component
public class Sender {

    @Autowired
    private JmsTemplate template;

    private final String RECEIVER_NAME = "dataBaseWatchDog";

    public void sendUpdateEvent(String entity, Object value) {
        Event event = new Event("update", entity, value.toString());
        template.convertAndSend(RECEIVER_NAME, event);
    }

    public void sendInsertEvent(String entity, Object value) {
        Event event = new Event("insert", entity, value.toString());
        template.convertAndSend(RECEIVER_NAME, event);
    }

    public void sendDeleteEvent(String entity, Object value) {
        Event event = new Event("delete", entity, value.toString());
        template.convertAndSend(RECEIVER_NAME, event);
    }
}
