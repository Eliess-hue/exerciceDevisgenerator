package com.eliess.devis.service;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import com.eliess.devis.entity.Client;
import com.eliess.devis.repository.ClientRepository;

import java.util.List;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
class ClientServiceTest {

    @Test
    void shouldCalculateTotal() {

        // GIVEN
        ClientRepository mockRepo = mock(ClientRepository.class);
        ClientService clientService = new ClientService(mockRepo);
        List<BigDecimal> prices = List.of(
        new BigDecimal("10.00"),
        new BigDecimal("20.00"),
        new BigDecimal("5.00")
                );

        // WHEN
        BigDecimal result = clientService.calculateTotal(prices);

        // THEN
        assertEquals(new BigDecimal("35.00"), result);

    }

    @Test
    void shouldReturnZeroWhenEmptyList() {

        //GIVEN
        ClientRepository mockRepo = mock(ClientRepository.class);
        ClientService clientService = new ClientService(mockRepo);
        List<BigDecimal> prices = List.of();

        // WHEN
        BigDecimal result = clientService.calculateTotal(prices);

        //THEN
        assertEquals(BigDecimal.ZERO, result);
                
    }

    @Test
    void shouleThrowExceptionWhenNegativePrice() {

        // GIVEN
        ClientRepository mockRepo = mock(ClientRepository.class);
        ClientService clientService = new ClientService(mockRepo);

        List<BigDecimal> prices = List.of(
        new BigDecimal("10.00"),
        new BigDecimal("-5.00")
        );

        // WHEN + THEN
        assertThrows(
                    IllegalArgumentException.class,
                    () -> clientService.calculateTotal(prices)
                );

    }

    @Test
    void shouldReturnAllClients() {

        // GIVEN
        // 1. Créer un mock de ClientRepository
        ClientRepository mockRepo = mock(ClientRepository.class);

        // 2. Créer un ClientService avec ce mock
        ClientService clientService = new ClientService(mockRepo);

        // 3. Préparer une liste de clients fictifs
        List<Client> fakeClients = List.of(
            new Client(1L, "Alice", "alice@email.com"),
            new Client(2L, "Bob", "bob@email.com")
            );

        // 4. Dire au mock : "quand on appelle findAll(), retourne cette liste"
        when(mockRepo.findAll()).thenReturn(fakeClients);

        // WHEN
        List<Client> result = clientService.getAllClients();

        // THEN
        assertEquals(2, result.size());
    }

}