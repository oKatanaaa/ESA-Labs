package com.example.lab1.utils;

import com.example.lab1.models.Car;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Scanner;


// Created to avoid recursion during GET request
public class CarSerializer extends StdSerializer<Car> {

    protected CarSerializer(Class<Car> t) {
        super(t);
    }

    @Override
    public void serialize(Car car, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", car.getId());
        jsonGenerator.writeStringField("model", car.getModel());
        //jsonGenerator.writeNumberField("driverId", car.getDriver().getId());
        //jsonGenerator.writeNumberField("shopId", car.getShop().getId());
        jsonGenerator.writeEndObject();
    }
}
