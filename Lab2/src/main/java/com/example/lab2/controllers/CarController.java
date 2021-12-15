package com.example.lab2.controllers;


import com.example.lab2.models.Car;
import com.example.lab2.models.Driver;
import com.example.lab2.models.Shop;
import com.example.lab2.services.CarService;
import com.example.lab2.services.DriverService;
import com.example.lab2.services.ShopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping(value = "/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private ShopService shopService;


    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/json") // localhost:8080/car
    public ResponseEntity getCars(){
        List<Car> cars = carService.findAll();
        return ResponseEntity.ok().body(cars);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/xml") // localhost:8080/shop
    public ModelAndView getCarsXSLT() throws JsonProcessingException {
        List<Car> cars = carService.findAll();
        ModelAndView modelAndView = new ModelAndView("cars");
        Source source = new StreamSource(new ByteArrayInputStream(new XmlMapper().writeValueAsBytes(cars)));
        modelAndView.addObject(source);
        return modelAndView;
    }

    @RequestMapping(value = "/{carId}", method = RequestMethod.GET) // localhost:8080/driver
    public ResponseEntity getCarById(@PathVariable("carId") Integer carId){
        Optional<Car> car = carService.findById(carId);

        if (!car.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Car with id %s not found", carId), HttpStatus.NOT_FOUND);

        return ResponseEntity.ok().body(car.get());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addNewCar(
            @RequestBody Car car,
            @RequestParam Integer driverId,
            @RequestParam Integer shopId
    ) {
        Optional<Driver> driver = driverService.findById(driverId);
        if (!driver.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Driver with id %s not found", driverId), HttpStatus.NOT_FOUND);

        car.setDriver(driver.get());

        Optional<Shop> shop = shopService.findById(shopId);
        if (!shop.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Shop with id %s not found", shopId), HttpStatus.NOT_FOUND);

        car.setShop(shop.get());

        carService.save(car);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{carId}", method = RequestMethod.PUT)
    public ResponseEntity updateCar(
            @RequestBody Car carUpdate,
            @PathVariable("carId") Integer carId,
            @RequestParam(defaultValue = "-1") Integer driverId,
            @RequestParam(defaultValue = "-1") Integer shopId
    ) {
        Optional<Car> carWrapper = carService.findById(carId);

        if (!carWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Car with id %s not found", carId), HttpStatus.NOT_FOUND);

        Car car = carWrapper.get();
        if (!carUpdate.getModel().isEmpty())
            car.setModel(carUpdate.getModel());

        Optional<Driver> driverWrapper = driverService.findById(driverId);
        if (!driverWrapper.isPresent() && driverId > 0)
            return new ResponseEntity<Object>(
                    String.format("Driver with id %s not found", driverId), HttpStatus.NOT_FOUND);
        car.setDriver(driverWrapper.get());

        Optional<Shop> shopWrapper = shopService.findById(shopId);
        if (!shopWrapper.isPresent() && shopId > 0)
            return new ResponseEntity<Object>(
                    String.format("Shop with id %s not found", shopId), HttpStatus.NOT_FOUND);
        car.setShop(shopWrapper.get());

        carService.save(car);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{carId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCar(@PathVariable("carId") Integer carId) {
        Optional<Car> carWrapper = carService.findById(carId);
        if (!carWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Car with id %s not found", carId), HttpStatus.NOT_FOUND);

        carService.delete(carWrapper.get());
        return ResponseEntity.ok().build();
    }

}
