package com.example.exchangeapi.service;

import com.example.exchangeapi.mapper.CurrencyMapper;
import com.example.exchangeapi.model.CurrencyRateDto;
import com.example.exchangeapi.repository.ExchangeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;


    public CurrencyRateDto getCurrency(String code) {
        return exchangeRepository.findByCode(code)
                .map(CurrencyMapper::mapCurrencyToDto)
                .orElseThrow(EntityNotFoundException::new);

    }
}
