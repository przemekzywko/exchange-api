package com.example.exchangeapi.repository;

import com.example.exchangeapi.model.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRepository extends JpaRepository<Currency, String> {

   Optional <Currency> findByCode(String code);
}
