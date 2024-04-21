package com.project.ezimenu.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "dishes")
@Data
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dishId")
    private long dishId;

    @Column(name = "dishName")
    private String dishName;

    @Column(name = "dishDescription")
    private String dishDescription;

    @Column(name = "dishPrice")
    private int dishPrice;

    @Column(name = "dishStatus")
    private String dishStatus;

    @ManyToOne
    @JoinColumn(name = "menuId")
    @JsonIgnoreProperties("dishes")
    private Menu menu;
}
