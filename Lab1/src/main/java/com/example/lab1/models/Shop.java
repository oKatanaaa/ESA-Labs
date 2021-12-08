package com.example.lab1.models;

import com.example.lab1.utils.DriverSerializer;
import com.example.lab1.utils.ShopSerializer;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="Shop")
@Data
@JsonSerialize(using = ShopSerializer.class)
public class Shop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Car> cars;
}
