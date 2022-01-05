package com.example.lab2.jms;


import com.example.lab2.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Topic;
import java.util.ArrayList;
import java.util.List;

// old Sender class
@Component
public class DataModificationTopic {

    ArrayList<EventListener> listenerList = new ArrayList<>();
    JmsTemplate jmsTemplate;
    Topic eventTopic;

    public DataModificationTopic(JmsTemplate jmsTemplate) throws JMSException {
        this.jmsTemplate = jmsTemplate;
        this.eventTopic = jmsTemplate.getConnectionFactory().createConnection()
                .createSession().createTopic("event");
    }

    public void subscribe(EventListener listener) {
        listenerList.add(listener);
    }

    public void sendUpdateEvent(String entity, Object value) {
        Event event = new Event("update", entity, value.toString());
        updateListeners(event);
    }

    public void sendInsertEvent(String entity, Object value) {
        Event event = new Event("insert", entity, value.toString());
        updateListeners(event);
    }

    public void sendDeleteEvent(String entity, Object value) {
        Event event = new Event("delete", entity, value.toString());
        updateListeners(event);
    }

    public void updateListeners(Event event) {
        jmsTemplate.convertAndSend(eventTopic, event);
        //for (EventListener listener: listenerList)
        //    listener.update(event);
    }
}
