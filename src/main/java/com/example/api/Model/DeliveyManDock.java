package com.example.api.Model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "GCARNXOFMOLL")
public class DeliveyManDock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "XOFCOD")
    private Integer deliveryManCod;

    @Column(name = "XOFNOM")
    private String deliveryManName;

    @Column(name = "XOFMOLLNUM")
    private String dockCode;

    @Column(name = "XOFMOLL")
    private String dockName;

    @Column(name = "XOFDATA")
    private Timestamp serviceDate;
}
