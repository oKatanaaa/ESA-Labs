package com.example.lab1.utils;


import com.example.lab1.models.Car;
import com.example.lab1.models.Shop;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

// Created to avoid recursion during GET request
public class ShopSerializer extends StdSerializer<Shop> {

    protected ShopSerializer(Class<Shop> t) {
        super(t);
    }

    @Override
    public void serialize(Shop shop, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("shopId", shop.getId());
        jsonGenerator.writeStringField("address", shop.getAddress());

        jsonGenerator.writeArrayFieldStart("carIds");
        for (Car car: shop.getCars())
            jsonGenerator.writeNumber(car.getId());
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
