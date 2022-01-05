package com.example.lab2.services;

import com.example.lab2.jms.DataModificationTopic;
import com.example.lab2.jms.EventListenerFactory;
import com.example.lab2.models.Driver;
import com.example.lab2.repositories.DriverRepository;
import com.example.lab2.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import java.util.List;
import java.util.Optional;

@Service
@Repository
@Transactional
public class DriverServiceImpl implements DriverService {
    private final DriverRepository repository;

    private DataModificationTopic dataModificationTopic;

    @Autowired
    public DriverServiceImpl(DriverRepository repository, JmsTemplate template) throws JMSException {
        this.repository = repository;
        dataModificationTopic = new DataModificationTopic(template);
        //dataModificationTopic.subscribe(factory.createEmailLoggerListener());
        //dataModificationTopic.subscribe(factory.createEventLoggerListener());
    }

    @Override
    public Optional<Driver> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Driver> findAll() {
        return Converter.iterableToArrayList(repository.findAll());
    }

    @Override
    public void save(Driver driver) {
        repository.save(driver);
        dataModificationTopic.sendInsertEvent("Driver", driver);
    }

    @Override
    public void delete(Driver driver) {
        repository.delete(driver);
        dataModificationTopic.sendDeleteEvent("Driver", driver);
    }

    @Override
    public List<Driver> findByName(String name) {
        return repository.findByName(name);
    }
}
