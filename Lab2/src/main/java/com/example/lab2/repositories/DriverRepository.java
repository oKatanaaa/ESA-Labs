package com.example.lab2.repositories;

import com.example.lab2.models.Driver;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DriverRepository extends CrudRepository<Driver, Integer> {
    List<Driver> findByName(String name);
}
