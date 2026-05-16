package com.eliess.devis.entity;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="quote_line")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuoteLine {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="quote_id", nullable=false)
    private Long quoteId;

    @Column(nullable=false)
    private String description;

    @Column(nullable=false)
    private Integer quantity;

    @Column(name="unit_price", nullable=false, precision=10, scale=2)
    private BigDecimal unitPrice;

}