package com.example.api.Testing;

import com.example.api.DTO.ArticleDto;
import com.example.api.DTO.ClientDto;
import com.example.api.DTO.LineOrderDto;
import com.example.api.DTO.ListingDto;
import com.example.api.Model.Article;
import com.example.api.Model.Client;
import com.example.api.Model.LiniesNoFacturades;
import com.example.api.Repository.*;
import com.example.api.Service.SummaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SummaryServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private LineasNoFacturadesRepository lineasNoFacturadesRepository;

    @Mock
    private DeliveryManDockRepository deliveryManDockRepository;

    @Mock
    private DeliveryManRepository deliveryManRepository;

    @InjectMocks
    private SummaryService summaryService;

    @Test
    public void testGetListings() {
        // Datos simulados de los repositorios
        List<Article> articles = List.of(
                new Article(1, "Article 1"),
                new Article(2, "Article 2")
        );
        List<Client> clients = List.of(
                new Client(1, "Client 1"),
                new Client(2, "Client 2")
        );

        // Configurar mocks para devolver entidades
        when(articleRepository.findAll()).thenReturn(articles);
        when(clientRepository.findAll()).thenReturn(clients);

        // Llamar al método que se está probando
        ListingDto result = summaryService.getListings();

        // Construir los DTOs esperados
        List<ArticleDto> expectedArticles = articles.stream()
                .map(art -> new ArticleDto(art.getArticleCode(), art.getArticleName()))
                .collect(Collectors.toList());
        List<ClientDto> expectedClients = clients.stream()
                .map(cli -> new ClientDto(cli.getClientId(), cli.getComercialName()))
                .collect(Collectors.toList());

        // Verificaciones de los resultados
        assertNotNull(result);
        assertEquals(expectedArticles.size(), result.getArticleList().size());
        assertEquals(expectedClients.size(), result.getClientList().size());
        for (int i = 0; i < expectedArticles.size(); i++) {
            assertEquals(expectedArticles.get(i).getName(), result.getArticleList().get(i).getName());
        }
        for (int i = 0; i < expectedClients.size(); i++) {
            assertEquals(expectedClients.get(i).getName(), result.getClientList().get(i).getName());
        }

        // Verificar interacciones con los mocks
        verify(articleRepository, times(1)).findAll();
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    public void testGetLineOrdersByDateAndFilter_FilterByArticle() {
        // Preparar datos simulados
        Timestamp testDate = Timestamp.valueOf("2024-04-08 00:00:00");
        List<LiniesNoFacturades> allOrders = List.of(
                new LiniesNoFacturades(1, 123, 12317, 20, "Observations", testDate, BigDecimal.ONE, BigDecimal.TEN, 30, "unit", "type", BigDecimal.ONE, BigDecimal.TEN, "unitType"),
                new LiniesNoFacturades(2, 124, 12317, 21, "Observations", testDate, BigDecimal.ONE, BigDecimal.TEN, 31, "unit", "type", BigDecimal.ONE, BigDecimal.TEN, "unitType"),
                new LiniesNoFacturades(3, 125, 11, 22, "Observations", testDate, BigDecimal.ONE, BigDecimal.TEN, 32, "unit", "type", BigDecimal.ONE, BigDecimal.TEN, "unitType")
        );

        // Configurar los mocks
        when(lineasNoFacturadesRepository.findAllByServiceDate(testDate)).thenReturn(allOrders);
        when(deliveryManDockRepository.findAllByServiceDate(any())).thenReturn(List.of());
        when(deliveryManRepository.findAll()).thenReturn(List.of());

        // Ejecutar la prueba
        List<LineOrderDto> result = summaryService.getLineOrdersByDateAndFilter(testDate, "A", 12317);

        // Verificaciones
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(line -> line.getArticleCode().equals(12317)));

        // Verificar interacciones con el mock
        verify(lineasNoFacturadesRepository, times(1)).findAllByServiceDate(testDate);
        verify(deliveryManRepository, times(1)).findAll();  // Verificar que se ha llamado al mock correctamente
    }


}
