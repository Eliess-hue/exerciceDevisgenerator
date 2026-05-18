package com.eliess.devis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eliess.devis.entity.QuoteLine;

public interface QuoteLineRepository extends JpaRepository<QuoteLine, Long> {

    List<QuoteLine> findByQuoteId(Long quoteId);

}
