package com.eliess.devis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eliess.devis.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
