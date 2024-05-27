package com.example.exchangeapi.sender;

import com.example.exchangeapi.model.ConvertConfirmationData;
import com.example.exchangeapi.properties.RabbitProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationDataSender {

    private final RabbitTemplate rabbitTemplate;

    private final RabbitProperties rabbitProperties;

    public void sendRates(ConvertConfirmationData confirmationData) {
        rabbitTemplate.convertAndSend(rabbitProperties.getConfirmationQueueName(), confirmationData);
    }
}
