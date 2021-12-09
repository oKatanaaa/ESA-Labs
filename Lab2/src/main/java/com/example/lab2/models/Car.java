package com.example.lab2.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Car")
@Data
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "model")
    private String model;

    @ManyToOne()
    @JoinColumn(name = "driverId")
    private Driver driver;

    @ManyToOne()
    @JoinColumn(name = "shopId")
    private Shop shop;
}
