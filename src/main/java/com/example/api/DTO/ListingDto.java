package com.example.api.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListingDto {
    private List<ArticleDto> articleList;
    private List<ClientDto> clientList;

    public ListingDto(List<ArticleDto> articleList, List<ClientDto> clientList) {
        this.articleList = articleList;
        this.clientList = clientList;
    }

}
