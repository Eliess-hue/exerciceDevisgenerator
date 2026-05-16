package com.eliess.devis.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.eliess.devis.entity.Client;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void shouldSaveAndFindClient() {

        // Arrange
        Client client = new Client(null, "Eliess", "eliess@test.com");
                client.setName("Eliess");
                client.setEmail("eliess@test.com");

        // Act
        Client savedClient = clientRepository.save(client);
        Optional<Client> foundClient =
                    clientRepository.findById(savedClient.getId());
                        
        // Assert
        assertTrue(foundClient.isPresent());
        assertEquals("Eliess", foundClient.get().getName());

    }

    @Test
    void shouldReturnEmptyWhenClientNotFound() {

        // Act
        Optional<Client> foundClient = clientRepository.findById(999L);
        
        // Assert
        assertTrue(foundClient.isEmpty());

    }

    @Test
    void shouldFailWhenEmailAlreadyExists() {

        // Arrange
        Client client1 = new Client();
                client1.setName("Jean");
                client1.setEmail("test@mail.com");

        Client client2 = new Client();
                client2.setName("Paul");
                client2.setEmail("test@mail.com");

        // Act
        clientRepository.save(client1);

        // Assert
        assertThrows(DataIntegrityViolationException.class,
            () -> {
            clientRepository.saveAndFlush(client2);
        });

    }

}