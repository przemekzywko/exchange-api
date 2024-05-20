package com.example.exchangeapi.service;

import com.example.exchangeapi.mapper.CurrencyMapper;
import com.example.exchangeapi.model.CurrencyRateDto;
import com.example.exchangeapi.model.ExchangeEvent;
import com.example.exchangeapi.model.entity.Currency;
import com.example.exchangeapi.rabbit.EventPublisher;
import com.example.exchangeapi.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final EventPublisher eventPublisher;

    public List<CurrencyRateDto> getAllCurrencies() {
        return exchangeRepository.findAll().stream()
                .map(CurrencyMapper::mapCurrencyToDto)
                .collect(Collectors.toList());
    }

    public CurrencyRateDto getCurrency(String code) {
        return exchangeRepository.findByCode(code)
                .map(CurrencyMapper::mapCurrencyToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency not found"));
    }

    public BigDecimal exchangeCurrency(String from, String to, BigDecimal amount) {
        Currency fromCurrency = exchangeRepository.findByCode(from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency not found"));
        Currency toCurrency = exchangeRepository.findByCode(to)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency not found"));

        BigDecimal rate = fromCurrency.getBid().divide(toCurrency.getAsk(), RoundingMode.HALF_UP);
        return amount.multiply(rate);
    }

    @Transactional
    public void exchangeCurrencyWithConfirmation(String from, String to, BigDecimal amount, String userEmail) {
        BigDecimal exchangedAmount = exchangeCurrency(from, to, amount);
        ExchangeEvent event = new ExchangeEvent(userEmail, from, to, amount, exchangedAmount);
        eventPublisher.publishExchangeEvent(event);
    }
}