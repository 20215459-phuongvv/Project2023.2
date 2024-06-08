package com.project.ezimenu.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bills")
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billId")
    private long billId;

    @Column(name = "totalAmount")
    private long totalAmount;

    @Column(name = "billDateTime")
    private LocalDateTime billDateTime;

    @Column(name = "status")
    private Short status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    @JsonIgnoreProperties("bill")
    private Order order;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("bill")
    private List<BillItem> billItems;
}
