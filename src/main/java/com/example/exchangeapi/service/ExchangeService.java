package com.example.exchangeapi.service;

import com.example.exchangeapi.mapper.CurrencyMapper;
import com.example.exchangeapi.model.ConvertConfirmationData;
import com.example.exchangeapi.model.ConvertRequest;
import com.example.exchangeapi.model.ConvertResult;
import com.example.exchangeapi.model.CurrencyRateDto;

import com.example.exchangeapi.model.entity.Currency;

import com.example.exchangeapi.repository.ExchangeRepository;
import com.example.exchangeapi.sender.ConfirmationDataSender;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ExchangeService {


    private final ExchangeRepository exchangeRepository;
    private final ConfirmationDataSender sender;
    private static final String HARD_CODED_EMAIL = "przemekzywko@gmail.com";

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

    public ConvertResult exchangeCurrency(ConvertRequest request) {
        Currency fromCurrency = exchangeRepository.findByCode(request.getFrom())
                .orElseThrow(() -> new EntityNotFoundException("Currency not found"));
        Currency toCurrency = exchangeRepository.findByCode(request.getTo())
                .orElseThrow(() -> new EntityNotFoundException("Currency not found"));

        BigDecimal rate = fromCurrency.getBid().divide(toCurrency.getAsk(), RoundingMode.HALF_UP);
        BigDecimal result = request.getAmount().multiply(rate);
        return ConvertResult.builder()
                .from(request.getFrom())
                .to(request.getTo())
                .amount(request.getAmount())
                .rate(rate)
                .result(result)
                .build();
    }

    public ConvertResult exchangeCurrencyWithConfirmation(ConvertRequest request) {
        ConvertResult convertResult = exchangeCurrency(request);
        ConvertConfirmationData confirmationData = ConvertConfirmationData.builder()
                .convertResult(convertResult)
                .email(HARD_CODED_EMAIL)
                .build();
        sender.sendRates(confirmationData);
        return convertResult;
    }
}