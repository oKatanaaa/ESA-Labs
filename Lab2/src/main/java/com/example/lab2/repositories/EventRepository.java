package com.example.lab2.repositories;

import com.example.lab2.models.Driver;
import com.example.lab2.models.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Integer> {
}
