package com.example.exchangeapi.rabbit;

import com.example.exchangeapi.model.ExchangeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publishExchangeEvent(ExchangeEvent event) {
        rabbitTemplate.convertAndSend("exchangeEventQueue", event);
    }

}