package com.example.lab2.services;

import com.example.lab2.jms.DataModificationTopic;
import com.example.lab2.jms.EventListenerFactory;
import com.example.lab2.models.Car;
import com.example.lab2.repositories.CarRepository;
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
public class CarServiceImpl implements CarService{
    private final CarRepository repository;

    private DataModificationTopic dataModificationTopic;

    @Autowired
    public CarServiceImpl(CarRepository repository, JmsTemplate template) throws JMSException {
        this.repository = repository;
        dataModificationTopic = new DataModificationTopic(template);
        //dataModificationTopic.subscribe(factory.createEventLoggerListener());
        //dataModificationTopic.subscribe(factory.createEventLoggerListener());
    }

    @Override
    public Optional<Car> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Car> findAll() {
        return Converter.iterableToArrayList(repository.findAll());
    }

    @Override
    public void save(Car car) {
        repository.save(car);
        dataModificationTopic.sendInsertEvent("Car", car);
    }

    @Override
    public void delete(Car car) {
        repository.delete(car);
        dataModificationTopic.sendDeleteEvent("Car", car);
    }
}
