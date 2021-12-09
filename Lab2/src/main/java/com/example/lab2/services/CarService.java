package com.example.lab2.services;

import com.example.lab2.models.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    Optional<Car> findById(Integer id);
    List<Car> findAll();
    void save(Car car);
    void delete(Car car);
}
