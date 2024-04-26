package com.example.api.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {
    private Integer code;
    private String name;


    public ClientDto(Integer clicod, String clinco) {
        this.code = clicod;
        this.name = clinco;
    }
}
