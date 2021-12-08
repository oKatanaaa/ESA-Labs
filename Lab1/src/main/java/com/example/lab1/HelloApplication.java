package com.example.lab1;

import com.example.lab1.controller.CarController;
import com.example.lab1.controller.DriverController;
import com.example.lab1.controller.ShopController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class HelloApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(DriverController.class);
        classes.add(CarController.class);
        classes.add(ShopController.class);
        return classes;
    }
}