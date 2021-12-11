package com.example.lab2.controllers;


import com.example.lab2.models.Shop;
import com.example.lab2.services.ShopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/shops")
public class ShopController {
    @Autowired
    private ShopService shopService;


    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/json") // localhost:8080/shop
    public ResponseEntity getShops() {
        List<Shop> shops = shopService.findAll();
        return ResponseEntity.ok().body(shops);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/xml") // localhost:8080/shop
    public ModelAndView getShopsXSLT() throws JsonProcessingException {
        List<Shop> shops = shopService.findAll();
        ModelAndView modelAndView = new ModelAndView("shops");
        Source source = new StreamSource(new ByteArrayInputStream(new XmlMapper().writeValueAsBytes(shops)));
        modelAndView.addObject(source);
        return modelAndView;
    }

    @RequestMapping(value = "/{shopId}", method = RequestMethod.GET) // localhost:8080/driver
    public ResponseEntity getShopById(@PathVariable("shopId") Integer shopId) {
        Optional<Shop> shop = shopService.findById(shopId);

        if (!shop.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Car with id %s not found", shopId), HttpStatus.NOT_FOUND);

        return ResponseEntity.ok().body(shop.get());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addNewShop(
            @RequestBody Shop shop
    ) {
        shopService.save(shop);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{shopId}", method = RequestMethod.PUT)
    public ResponseEntity updateShop(
            @RequestBody Shop shopUpdate,
            @PathVariable("shopId") Integer shopId
    ) {
        Optional<Shop> shopWrapper = shopService.findById(shopId);
        if (!shopWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Car with id %s not found", shopId), HttpStatus.NOT_FOUND);

        Shop shop = shopWrapper.get();
        if (!shopUpdate.getAddress().isEmpty())
            shop.setAddress(shopUpdate.getAddress());

        shopService.save(shop);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{shopId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteShop(@PathVariable("shopId") Integer shopId) {
        Optional<Shop> shopWrapper = shopService.findById(shopId);
        if (!shopWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Shop with id %s not found", shopId), HttpStatus.NOT_FOUND);

        shopService.delete(shopWrapper.get());
        return ResponseEntity.ok().build();
    }
}
