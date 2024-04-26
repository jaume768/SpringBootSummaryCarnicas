package com.example.api.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {
    private Long code;
    private String name;


    public ClientDto(Long clicod, String clinco) {
        this.code = clicod;
        this.name = clinco;
    }
}
