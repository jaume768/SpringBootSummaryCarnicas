package com.example.api.Service;

import com.example.api.DTO.ArticleDto;
import com.example.api.DTO.ClientDto;
import com.example.api.DTO.LineOrderDto;
import com.example.api.DTO.ListingDto;
import com.example.api.Model.*;
import com.example.api.Repository.ArticleRepository;
import com.example.api.Repository.ClientRepository;
import com.example.api.Repository.DeliveryManRepository;
import com.example.api.Repository.LineasNoFacturadesRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SummaryService {
    private final ClientRepository clientRepository;
    private final ArticleRepository articleRepository;
    private final LineasNoFacturadesRepository lineasNoFacturadesRepository;
    private final DeliveryManRepository deliveryManRepository;

    public SummaryService(ClientRepository clientRepository, ArticleRepository articleRepository,
                          LineasNoFacturadesRepository lineasNoFacturadesRepository,
                          DeliveryManRepository deliveryManRepository) {
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.lineasNoFacturadesRepository = lineasNoFacturadesRepository;
        this.deliveryManRepository = deliveryManRepository;
    }

    public ListingDto getListings() {
        List<ArticleDto> articleList = articleRepository.findAll().stream()
                .map(art -> new ArticleDto(art.getArticleCode(), art.getArticleName()))
                .collect(Collectors.toList());

        List<ClientDto> clientList = clientRepository.findAll().stream()
                .map(cli -> new ClientDto(cli.getClientId(), cli.getComercialName()))
                .collect(Collectors.toList());

        return new ListingDto(articleList, clientList);
    }

    public List<LineOrderDto> getLineOrdersByDateAndFilter(Timestamp fecha, String filterType, Integer filterCode) {
        List<LiniesNoFacturades> orders = lineasNoFacturadesRepository.findAllByServiceDate(fecha);

        if ("A".equals(filterType)) {
            orders = orders.stream()
                    .filter(order -> order.getArticleCode().equals(filterCode))
                    .collect(Collectors.toList());
        } else if ("C".equals(filterType)) {
            orders = orders.stream()
                    .filter(order -> order.getClientCode().equals(filterCode))
                    .collect(Collectors.toList());
        }

        Map<Float, String> articles = articleRepository.findAll().stream()
                .collect(Collectors.toMap(Article::getArticleCode, Article::getArticleName));

        Map<Long, String> clients = clientRepository.findAll().stream()
                .collect(Collectors.toMap(Client::getClientId, Client::getComercialName));

        Map<Integer, String> deliveryMen = deliveryManRepository.findAll().stream()
                .collect(Collectors.toMap(DeliveryMan::getId, DeliveryMan::getDeliveryManName));

        return orders.stream().map(order -> mapToDto(order, articles, clients, deliveryMen)).collect(Collectors.toList());
    }

    private LineOrderDto mapToDto(LiniesNoFacturades order, Map<Float, String> articles, Map<Long, String> clients, Map<Integer, String> deliveryMen) {
        LineOrderDto dto = new LineOrderDto();
        dto.setId(order.getLineId());
        dto.setOrderNumber(order.getDocumentNumber());
        dto.setArticleCode(order.getArticleCode());
        dto.setArticleName(articles.getOrDefault(order.getArticleCode(), "Unknown"));
        dto.setUnits(order.getServedUnits().compareTo(BigDecimal.ZERO) == 0 ? order.getServedKilos() : order.getServedUnits());
        dto.setUnitType(order.getTypeunit());
        dto.setClientCode(order.getClientCode().longValue());
        dto.setClientName(clients.getOrDefault(order.getClientCode().longValue(), "Unknown"));
        dto.setObservation(order.getLineObservation());
        dto.setDeliveryManCode(Long.valueOf(order.getDeliveryPersonAssigned()));
        dto.setDeliveryManName(deliveryMen.getOrDefault(order.getDeliveryPersonAssigned(), "Unknown"));
        return dto;
    }
}
