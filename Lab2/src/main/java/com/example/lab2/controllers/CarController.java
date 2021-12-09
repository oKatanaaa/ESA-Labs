package com.example.lab2.controllers;


import com.example.lab2.models.Car;
import com.example.lab2.models.Driver;
import com.example.lab2.services.CarService;
import com.example.lab2.services.DriverService;
import com.example.lab2.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/car")
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private ShopService shopService;


    @RequestMapping(value = "/", method = RequestMethod.GET) // localhost:8080/car
    public ResponseEntity<Object> getCars(){
        List<Car> cars = carService.findAll();
        return ResponseEntity.ok().body(cars);
    }

    @RequestMapping(value = "/{carId}", method = RequestMethod.GET) // localhost:8080/driver
    public ResponseEntity getCarById(@PathVariable("carId") Integer carId){
        Optional<Car> car = carService.findById(carId);

        if (!car.isPresent())
            return new ResponseEntity<Object>(String.format("Car with id %s not found", carId), HttpStatus.NOT_FOUND);

        return ResponseEntity.ok().body(car.get());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addNewCar(
            @RequestBody Car car,
            @RequestParam Integer driverId,
            @RequestParam Integer shopId
    ) {
        Optional<Driver> driver = driverService.findById(driverId);
        if (driver == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Driver with id %s not found", driverId)).build();
        car.setDriver(driver);

        Shop shop = shopService.get(Integer.valueOf(shopId));
        if (shop == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Shop with id %s not found", shopId)).build();
        car.setShop(shop);

        carService.save(car);
        return Response.ok().build();
    }

    @PUT
    @Path("/{carId}")
    public Response updateCar(
            @PathParam("carId") String carId,
            @DefaultValue("") @FormParam("model") String model
    ) {
        Car car = carService.get(Integer.valueOf(carId));
        if (car == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Car with id %s not found", carId)).build();

        if (!model.isEmpty())
            car.setModel(model);

        carService.update(car);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{carId}")
    public Response deleteCar(@PathParam("carId") String carId) {
        Car car = carService.get(Integer.valueOf(carId));
        if (car == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Car with id %s not found", carId)).build();

        carService.delete(car);
        return Response.ok().build();
    }
}
