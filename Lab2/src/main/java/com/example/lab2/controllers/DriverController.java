package com.example.lab2.controllers;


import com.example.lab2.models.Car;
import com.example.lab2.models.Driver;
import com.example.lab2.models.Shop;
import com.example.lab2.services.DriverService;
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
@RequestMapping(value = "/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/json") // localhost:8080/driver
    public ResponseEntity getDrivers() {
        List<Driver> drivers = driverService.findAll();
        return ResponseEntity.ok().body(drivers);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/xml") // localhost:8080/shop
    public ModelAndView getDriversXSLT() throws JsonProcessingException {
        List<Driver> drivers = driverService.findAll();
        ModelAndView modelAndView = new ModelAndView("drivers");
        Source source = new StreamSource(new ByteArrayInputStream(new XmlMapper().writeValueAsBytes(drivers)));
        modelAndView.addObject(source);
        return modelAndView;
    }

    @RequestMapping(value = "/{driverId}", method = RequestMethod.GET) // localhost:8080/driver
    public ResponseEntity getDriverById(@PathVariable("driverId") Integer driverId) {
        Optional<Driver> driver = driverService.findById(driverId);

        if (!driver.isPresent())
            return new ResponseEntity<Object>(String.format("Driver with id %s not found", driverId), HttpStatus.NOT_FOUND);

        return ResponseEntity.ok().body(driver.get());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addNewDriver(@RequestBody Driver driver) {
        driverService.save(driver);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{driverId}", method = RequestMethod.PUT)
    public ResponseEntity updateDriver(
            @RequestBody Driver driverUpdate,
            @PathVariable("driverId") Integer driverId
    ) {
        Optional<Driver> driverWrapper = driverService.findById(driverId);
        if (!driverWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Driver with id %s not found", driverId), HttpStatus.NOT_FOUND);

        Driver driver = driverWrapper.get();
        if (!driverUpdate.getEmail().isEmpty())
            driver.setEmail(driverUpdate.getEmail());

        if (!driverUpdate.getName().isEmpty())
            driver.setName(driverUpdate.getName());

        driverService.save(driver);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{driverId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDriver(@PathVariable("driverId") Integer driverId) {
        Optional<Driver> driverWrapper = driverService.findById(driverId);
        if (!driverWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Driver with id %s not found", driverId), HttpStatus.NOT_FOUND);

        driverService.delete(driverWrapper.get());
        return ResponseEntity.ok().build();
    }
}
