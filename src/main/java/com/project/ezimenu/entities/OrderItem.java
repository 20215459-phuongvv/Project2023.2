package com.project.ezimenu.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "orderItems")
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItemId")
    private long orderItemId;

    @ManyToOne
    @JoinColumn(name = "dishId")
    private Dish dish;

    @Column(name = "dishQuantity")
    private int dishQuantity;

    @Column(name = "customPrice")
    private int customPrice;

    @Column(name = "dishNote")
    private String dishNote;

    @Column(name = "dishStatus")
    private String dishStatus;

    @ManyToOne
    @JoinColumn(name = "orderId")
    @JsonIgnoreProperties("orderItems")
    private Order order;
}
