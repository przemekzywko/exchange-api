package com.example.exchangeapi.controller;

import com.example.exchangeapi.model.CurrencyRateDto;
import com.example.exchangeapi.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/currencies")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping("/{code}")
    public ResponseEntity<CurrencyRateDto> getCurrency(@PathVariable String code) {
        return ResponseEntity.status(HttpStatus.OK).body(exchangeService.getCurrency(code));
    }
}
