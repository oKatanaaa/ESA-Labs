package com.example.lab2.services;

import com.example.lab2.models.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverService {
    Optional<Driver> findById(Integer id);
    List<Driver> findAll();
    void save(Driver driver);
    void delete(Driver driver);
    List<Driver> findByName(String name);

}
