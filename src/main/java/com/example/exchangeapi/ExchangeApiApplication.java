package com.example.exchangeapi;

import com.example.exchangeapi.properties.RabbitProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RabbitProperties.class)
public class ExchangeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeApiApplication.class, args);
	}

}
