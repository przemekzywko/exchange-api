package com.example.exchangeapi.mapper;

import com.example.exchangeapi.model.CurrencyRateDto;
import com.example.exchangeapi.model.entity.Currency;


public class CurrencyMapper {

    public static CurrencyRateDto mapCurrencyToDto(Currency currency) {
        return CurrencyRateDto.builder()
                .currency(currency.getCurrency())
                .code(currency.getCode())
                .bid(currency.getBid())
                .ask(currency.getAsk())
                .build();
    }
}
