package com.example.lab2.services;

import com.example.lab2.models.Car;
import com.example.lab2.repositories.CarRepository;
import com.example.lab2.repositories.ShopRepository;
import com.example.lab2.utils.Converter;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Repository
@Transactional
public class CarServiceImpl implements CarService{
    private final CarRepository repository;

    public CarServiceImpl(CarRepository repository) {
        this.repository = repository;
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
    }

    @Override
    public void delete(Car car) {
        repository.delete(car);
    }
}