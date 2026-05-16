package com.eliess.devis.service;

import java.math.BigDecimal;
import java.util.List;

import com.eliess.devis.entity.Client;
import com.eliess.devis.repository.ClientRepository;

public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public BigDecimal calculateTotal(List<BigDecimal> prices) {

        BigDecimal total = BigDecimal.ZERO;

        for (BigDecimal price : prices) {
            if (price.compareTo(BigDecimal.ZERO) < 0) {

                throw new IllegalArgumentException("Le prix nepeut pas etre negatif");
                }

                total = total.add(price);

                }

        return total;

    }
}