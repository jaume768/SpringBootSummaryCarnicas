package com.example.api.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDto {
    private Float code;
    private String name;

    public ArticleDto(Float artcod, String artdes) {
        this.code = artcod;
        this.name = artdes;
    }
}