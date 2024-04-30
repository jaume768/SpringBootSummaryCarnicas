package com.example.api.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class LineOrderDto {
    private Integer id;
    private Integer orderNumber;
    private Integer articleCode;
    private String articleName;
    private BigDecimal units;
    private String unitType;
    private Long clientCode;
    private String clientName;
    private String observation;
    private Long deliveryManCode;
    private String deliveryManName;
    private String dockCode;
    private String dockName;
    private String orderType;
    private BigDecimal orderUnit;
    private String orderTypeUnit;

    // Constructor, getters y setters

    public void setUnits(BigDecimal alluni, BigDecimal allkgs) {
        this.units = (alluni.compareTo(BigDecimal.ZERO) == 0) ? allkgs : alluni;
    }
}
