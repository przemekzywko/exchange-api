package com.example.exchangeapi.controller;

import com.example.exchangeapi.model.CurrencyRateDto;
import com.example.exchangeapi.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CurrencyRateDto> getCurrency(@PathVariable String code) {
        CurrencyRateDto currency = exchangeService.getCurrency(code);
        return new ResponseEntity<>(currency, HttpStatus.OK);
    }

    @PostMapping("/exchange")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BigDecimal> exchangeCurrency(@RequestParam String from,
                                                       @RequestParam String to,
                                                       @RequestParam BigDecimal amount) {
        BigDecimal exchangedAmount = exchangeService.exchangeCurrency(from, to, amount);
        return new ResponseEntity<>(exchangedAmount, HttpStatus.OK);
    }

    @PostMapping("/exchange/confirm")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> exchangeCurrencyWithConfirmation(@RequestParam String from,
                                                                   @RequestParam String to,
                                                                   @RequestParam BigDecimal amount,
                                                                   @RequestParam String email) {
        exchangeService.exchangeCurrencyWithConfirmation(from, to, amount, email);
        return new ResponseEntity<>("Exchange completed and confirmation sent to " + email, HttpStatus.OK);
    }
}