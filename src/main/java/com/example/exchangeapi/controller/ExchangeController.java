package com.example.exchangeapi.controller;

import com.example.exchangeapi.model.ConvertRequest;
import com.example.exchangeapi.model.ConvertResult;
import com.example.exchangeapi.model.CurrencyRateDto;
import com.example.exchangeapi.model.ExchangeRequest;
import com.example.exchangeapi.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/currencies")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping
    public ResponseEntity<List<CurrencyRateDto>> getAllCurrencies() {
        List<CurrencyRateDto> currencies = exchangeService.getAllCurrencies();
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CurrencyRateDto> getCurrency(@PathVariable String code) {
        CurrencyRateDto currency = exchangeService.getCurrency(code);
        return new ResponseEntity<>(currency, HttpStatus.OK);
    }

    @PostMapping("/exchange")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ConvertResult> exchangeCurrency(@RequestBody ConvertRequest request) {
        ConvertResult result = exchangeService.exchangeCurrency(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/exchange/confirm")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ConvertResult> exchangeCurrencyWithConfirmation(@RequestBody ConvertRequest request) {
        ConvertResult result = exchangeService.exchangeCurrencyWithConfirmation(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}