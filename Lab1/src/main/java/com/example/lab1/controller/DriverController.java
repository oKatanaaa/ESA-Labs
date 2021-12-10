package com.example.lab1.controller;

import com.example.lab1.DAO.DriverDao;
import com.example.lab1.models.Driver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.List;

@WebServlet
@Path("/drivers")
public class DriverController {
    @EJB
    private DriverDao driverDao;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("/") // localhost:8080/driver
    public Response getDrivers() throws JsonProcessingException {
        List<Driver> drivers = driverDao.getAll();
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(drivers))
                .build();
    }

    @GET
    @Path("/{driverId}") // localhost:8080/driver
    public Response getDriverById(@PathParam("driverId") String driverId) throws JsonProcessingException {
        Driver driver = driverDao.get(Integer.valueOf(driverId));

        if (driver == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Driver with id %s not found", driverId)).build();

        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(driver))
                .build();
    }

    @POST
    @Path("/")
    public Response addNewDriver(
            @FormParam("name") String name,
            @FormParam("email") String email) {
        Driver driver = new Driver();
        driver.setName(name);
        driver.setEmail(email);

        driverDao.save(driver);
        return Response.ok().build();
    }

    @PUT
    @Path("/{driverId}")
    public Response updateDriver(
            @PathParam("driverId") String driverId,
            @DefaultValue("") @FormParam("name") String name,
            @DefaultValue("") @FormParam("email") String email
    ) {
        Driver driver = driverDao.get(Integer.valueOf(driverId));
        if (driver == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Driver with id %s not found", driverId)).build();

        if (!name.isEmpty())
            driver.setName(name);

        if (!email.isEmpty())
            driver.setEmail(email);

        driverDao.update(driver);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{driverId}")
    public Response deleteDriver(@PathParam("driverId") String driverId) {
        Driver driver = driverDao.get(Integer.valueOf(driverId));
        if (driver == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Driver with id %s not found", driverId)).build();

        driverDao.delete(driver);
        return Response.ok().build();
    }
}
