package com.example.exchangeapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeEvent {
    private String email;
    private String from;
    private String to;
    private BigDecimal amount;
    private BigDecimal exchangedAmount;
}