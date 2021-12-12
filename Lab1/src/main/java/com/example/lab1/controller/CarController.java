package com.example.lab1.controller;

import com.example.lab1.dao.CarDao;
import com.example.lab1.dao.DriverDao;
import com.example.lab1.dao.ShopDao;
import com.example.lab1.models.Car;
import com.example.lab1.models.Driver;
import com.example.lab1.models.Shop;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@WebServlet
@Path("/cars")
public class CarController {
    @EJB
    private CarDao carDao;

    @EJB
    private DriverDao driverDao;

    @EJB
    private ShopDao shopDao;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("/") // localhost:8080/car
    public Response getCars() throws JsonProcessingException {
        List<Car> cars = carDao.getAll();
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(cars))
                .build();
    }

    @GET
    @Path("/{carId}") // localhost:8080/driver
    public Response getCarById(@PathParam("carId") String carId) throws JsonProcessingException {
        Car car = carDao.get(Integer.valueOf(carId));

        if (car == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Car with id %s not found", carId)).build();

        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(car))
                .build();
    }

    @POST
    @Path("/")
    public Response addNewCar(
            @FormParam("model") String model,
            @FormParam("driverId") String driverId,
            @FormParam("shopId") String shopId) {
        Car car = new Car();
        car.setModel(model);

        Driver driver = driverDao.get(Integer.valueOf(driverId));
        if (driver == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Driver with id %s not found", driverId)).build();
        car.setDriver(driver);

        Shop shop = shopDao.get(Integer.valueOf(shopId));
        if (shop == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Shop with id %s not found", shopId)).build();
        car.setShop(shop);

        carDao.save(car);
        return Response.ok().build();
    }

    @PUT
    @Path("/{carId}")
    public Response updateCar(
            @PathParam("carId") String carId,
            @DefaultValue("") @FormParam("model") String model,
            @DefaultValue("-1") @FormParam("driverId") String driverId,
            @DefaultValue("-1") @FormParam("shopId") String shopId
    ) {
        Car car = carDao.get(Integer.valueOf(carId));
        if (car == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Car with id %s not found", carId)).build();

        if (!model.isEmpty())
            car.setModel(model);

        if (!driverId.equals("-1")) {
            Driver driver = driverDao.get(Integer.valueOf(driverId));
            if (driver == null)
                return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                        .entity(String.format("Driver with id %s not found", driverId)).build();
            car.setDriver(driver);
        }

        if (!shopId.equals("-1")) {
            Shop shop = shopDao.get(Integer.valueOf(shopId));
            if (shop == null)
                return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                        .entity(String.format("Shop with id %s not found", shopId)).build();
            car.setShop(shop);
        }

        carDao.update(car);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{carId}")
    public Response deleteCar(@PathParam("carId") String carId) {
        Car car = carDao.get(Integer.valueOf(carId));
        if (car == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Car with id %s not found", carId)).build();

        carDao.delete(car);
        return Response.ok().build();
    }
}
