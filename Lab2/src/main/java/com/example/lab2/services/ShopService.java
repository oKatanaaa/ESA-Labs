package com.example.lab2.services;

import com.example.lab2.models.Shop;

import java.util.List;
import java.util.Optional;

public interface ShopService {
    Optional<Shop> findById(Integer id);
    List<Shop> findAll();
    void save(Shop shop);
    void delete(Shop shop);
}
