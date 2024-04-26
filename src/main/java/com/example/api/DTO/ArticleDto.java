package com.example.api.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDto {
    private Integer code;
    private String name;

    public ArticleDto(Integer artcod, String artdes) {
        this.code = artcod;
        this.name = artdes;
    }
}