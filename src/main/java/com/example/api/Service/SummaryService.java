package com.example.api.Service;

import com.example.api.DTO.ArticleDto;
import com.example.api.DTO.ClientDto;
import com.example.api.DTO.LineOrderDto;
import com.example.api.DTO.ListingDto;
import com.example.api.Model.*;
import com.example.api.Repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SummaryService {
    private final ClientRepository clientRepository;
    private final ArticleRepository articleRepository;
    private final LineasNoFacturadesRepository lineasNoFacturadesRepository;
    private final DeliveryManRepository deliveryManRepository;
    private final DeliveryManDockRepository deliveryManDockRepository; // Repositorio para la tabla intermedia

    public SummaryService(ClientRepository clientRepository, ArticleRepository articleRepository,
                          LineasNoFacturadesRepository lineasNoFacturadesRepository,
                          DeliveryManRepository deliveryManRepository, DeliveryManDockRepository deliveryManDockRepository) {
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.lineasNoFacturadesRepository = lineasNoFacturadesRepository;
        this.deliveryManRepository = deliveryManRepository;
        this.deliveryManDockRepository = deliveryManDockRepository;
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
        List<DeliveyManDock> deliveyManDocks = deliveryManDockRepository.findAllByServiceDate(fecha);

        Map<Integer, DeliveyManDock> dockMap = deliveyManDocks.stream()
                .collect(Collectors.toMap(DeliveyManDock::getDeliveryManCod, Function.identity()));

        if ("A".equals(filterType)) {
            orders = orders.stream()
                    .filter(order -> order.getArticleCode().equals(filterCode))
                    .collect(Collectors.toList());
        } else if ("C".equals(filterType)) {
            orders = orders.stream()
                    .filter(order -> order.getClientCode().equals(filterCode))
                    .collect(Collectors.toList());
        }

        Map<Integer, String> articles = articleRepository.findAll().stream()
                .collect(Collectors.toMap(Article::getArticleCode, Article::getArticleName));

        Map<Integer, String> clients = clientRepository.findAll().stream()
                .collect(Collectors.toMap(Client::getClientId, Client::getComercialName));

        Map<Integer, String> deliveryMen = deliveryManRepository.findAll().stream()
                .collect(Collectors.toMap(DeliveryMan::getDeliveryManId, DeliveryMan::getDeliveryManName));

        return orders.stream().map(order -> mapToDto(order, articles, clients, deliveryMen, dockMap)).collect(Collectors.toList());
    }

    private LineOrderDto mapToDto(LiniesNoFacturades order, Map<Integer, String> articles, Map<Integer, String> clients, Map<Integer, String> deliveryMen, Map<Integer, DeliveyManDock> dockMap) {
        LineOrderDto dto = new LineOrderDto();
        dto.setId(order.getLineId());
        dto.setOrderNumber(order.getDocumentNumber());
        dto.setArticleCode(order.getArticleCode());
        dto.setArticleName(articles.getOrDefault(order.getArticleCode(), "Unknown"));
        dto.setUnits(order.getServedUnits().compareTo(BigDecimal.ZERO) == 0 ? order.getServedKilos() : order.getServedUnits());
        dto.setUnitType(order.getTypeunit());
        dto.setClientCode(order.getClientCode().longValue());
        dto.setClientName(clients.get(order.getClientCode()));
        dto.setObservation(order.getLineObservation());
        dto.setDeliveryManCode(Long.valueOf(order.getDeliveryPersonAssigned()));
        dto.setDeliveryManName(deliveryMen.getOrDefault(order.getDeliveryPersonAssigned(), "Unknown"));

        DeliveyManDock dock = dockMap.get(order.getDeliveryPersonAssigned());

        if (dock != null) {
            dto.setDockCode(dock.getDockCode().trim());
            dto.setDockName(dock.getDockName().trim());
        } else {
            dto.setDockCode(null);
            dto.setDockName(null);
        }

        dto.setOrderType(order.getOrderType());
        if (order.getUnitsOrder().compareTo(BigDecimal.ZERO) > 0) {
            dto.setOrderUnit(order.getUnitsOrder());
        } else if (order.getKgOrder().compareTo(BigDecimal.ZERO) > 0) {
            dto.setOrderUnit(order.getKgOrder());
        } else {
            dto.setOrderUnit(BigDecimal.ZERO);
        }
        dto.setOrderTypeUnit(order.getOrderUnitType());

        return dto;
    }
}
