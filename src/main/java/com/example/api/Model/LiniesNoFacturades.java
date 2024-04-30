package com.example.api.Model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Getter
@Setter
@Entity
@Table(name = "GCARNALL")
public class LiniesNoFacturades {

    @Id
    @Column(nullable = false, name = "IDX")
    private Integer lineId;

    @Column(nullable = false, name = "ALLNUM")
    private Integer documentNumber;

    @Column(name = "ALLCOD")
    private Integer articleCode;

    @Column(name = "ALLCLI")
    private Integer clientCode;

    @Column(name = "ALLDES")
    private String lineObservation;

    @Column(nullable = false, name = "ALLFET")
    private Timestamp serviceDate;

    @Column(name = "ALLUNI")
    private BigDecimal servedUnits;

    @Column(name = "ALLKGS")
    private BigDecimal servedKilos;

    @Column(name = "ALLXOF")
    private Integer deliveryPersonAssigned;

    @Column(name = "ALLUMI")
    private String typeunit;

    @Column(name = "ALLTIP")
    private String orderType;

    @Column(name = "ALLUNC")
    private BigDecimal unitsOrder;

    @Column(name = "ALLKGC")
    private BigDecimal kgOrder;

    @Column(name = "ALLTUN")
    private String orderUnitType;

}
