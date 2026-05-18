package com.eliess.devis.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eliess.devis.entity.Quote;
import com.eliess.devis.entity.QuoteLine;
import com.eliess.devis.repository.QuoteLineRepository;
import com.eliess.devis.repository.QuoteRepository;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final QuoteRepository quoteRepository;
    private final QuoteLineRepository quoteLineRepository;

    public QuoteController(
            QuoteRepository quoteRepository,
            QuoteLineRepository quoteLineRepository
    ) {
        this.quoteRepository = quoteRepository;
        this.quoteLineRepository = quoteLineRepository;
    }

    @GetMapping
    public ResponseEntity<List<Quote>> getAllQuotes() {
        return ResponseEntity.ok(quoteRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quote> getQuoteById(@PathVariable Long id) {

        return quoteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Quote> createQuote(@RequestBody Quote quote) {

        Quote saved = quoteRepository.save(quote);

        return ResponseEntity.status(201).body(saved);
    }

    @PostMapping("/{id}/lines")
    public ResponseEntity<QuoteLine> addLineToQuote(
            @PathVariable Long id,
            @RequestBody QuoteLine quoteLine
    ) {

        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quote not found"));

        quoteLine.setQuoteId(quote.getId());

        QuoteLine saved = quoteLineRepository.save(quoteLine);

        return ResponseEntity.status(201).body(saved);
    }

}