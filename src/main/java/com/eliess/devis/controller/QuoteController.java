package com.eliess.devis.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eliess.devis.dto.QuoteDTO;
import com.eliess.devis.entity.Client;
import com.eliess.devis.entity.Quote;
import com.eliess.devis.entity.QuoteLine;
import com.eliess.devis.repository.ClientRepository;
import com.eliess.devis.repository.QuoteLineRepository;
import com.eliess.devis.repository.QuoteRepository;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final QuoteRepository quoteRepository;
    private final QuoteLineRepository quoteLineRepository;
    private final ClientRepository clientRepository;

    public QuoteController(
            QuoteRepository quoteRepository,
            QuoteLineRepository quoteLineRepository,
            ClientRepository clientRepository
    ) {
        this.quoteRepository = quoteRepository;
        this.quoteLineRepository = quoteLineRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public ResponseEntity<List<QuoteDTO>> getAllQuotes() {
        List<Quote> quotes = quoteRepository.findAll();
        List<QuoteDTO> dtos = quotes.stream()
            .map(quote -> {
                Client client = clientRepository.findById(quote.getClientId())
                    .orElse(null);
                String clientName = client != null ? client.getName() : "Inconnu";
                return new QuoteDTO(
                    quote.getId(),
                    clientName,
                    quote.getStatus(),
                    quote.getCreatedAt()
                );
            })
            .toList();
        return ResponseEntity.ok(dtos);
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

    // QuoteLine
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

    @PutMapping("/{id}")
    public ResponseEntity<Quote> updateQuote(
            @PathVariable Long id,
            @RequestBody Quote updatedQuote
    ) {

        return quoteRepository.findById(id)
                .map(quote -> {

                    quote.setStatus(updatedQuote.getStatus());

                    Quote saved = quoteRepository.save(quote);

                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {

        quoteRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    // QuoteLine
    @GetMapping("/{id}/lines")
    public ResponseEntity<List<QuoteLine>> getQuoteLines(
        @PathVariable Long id) {
        return ResponseEntity.ok(
            quoteLineRepository.findByQuoteId(id)
        );
    }

    // QuoteLine
    @DeleteMapping("/{id}/lines/{lineId}")
    public ResponseEntity<Void> deleteQuoteLine(
        @PathVariable Long id,
        @PathVariable Long lineId) {
        quoteLineRepository.deleteById(lineId);
        return ResponseEntity.noContent().build();
    }

}