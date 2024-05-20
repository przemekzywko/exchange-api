package com.example.exchangeapi.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CurrencyRateDto {

    private String code;
    private String currency;
    private BigDecimal bid;
    private BigDecimal ask;
}
