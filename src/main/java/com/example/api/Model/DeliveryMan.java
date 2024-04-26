package com.example.api.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "GCARNXOF")
public class DeliveryMan {
    @Id
    @Column(nullable = false, name = "IDX")
    private Integer id;

    @Column(name = "XOFCOD")
    private Long deliveryManId;

    @Column(name = "XOFNOM")
    private String deliveryManName;

}
