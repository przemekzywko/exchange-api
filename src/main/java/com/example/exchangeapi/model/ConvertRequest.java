package com.example.exchangeapi.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConvertRequest {
    private String from;
    private String to;
    private BigDecimal amount;
}
