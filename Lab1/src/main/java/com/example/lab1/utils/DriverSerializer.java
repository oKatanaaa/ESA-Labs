package com.example.lab1.utils;

import com.example.lab1.models.Car;
import com.example.lab1.models.Driver;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;


// Created to avoid recursion during GET request
public class DriverSerializer extends StdSerializer<Driver> {


    protected DriverSerializer(Class<Driver> t) {
        super(t);
    }

    @Override
    public void serialize(Driver driver, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("driverId", driver.getId());
        jsonGenerator.writeStringField("email", driver.getEmail());

        jsonGenerator.writeArrayFieldStart("carIds");
        for (Car car: driver.getCars())
            jsonGenerator.writeNumber(car.getId());
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
