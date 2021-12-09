package com.example.lab2.services;

import com.example.lab2.models.Shop;
import com.example.lab2.repositories.ShopRepository;
import com.example.lab2.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Repository
@Transactional
public class ShopServiceImpl implements ShopService{
    private final ShopRepository repository;

    @Autowired
    public ShopServiceImpl(ShopRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Shop> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Shop> findAll() {
        return Converter.iterableToArrayList(repository.findAll());
    }

    @Override
    public void save(Shop shop) {
        repository.save(shop);
    }

    @Override
    public void delete(Shop shop) {
        repository.delete(shop);
    }
}
