package com.eliess.devis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eliess.devis.entity.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

}
