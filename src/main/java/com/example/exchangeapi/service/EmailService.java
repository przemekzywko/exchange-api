package com.example.exchangeapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendExchangeConfirmation(String email, String from, String to, BigDecimal amount, BigDecimal exchangedAmount) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Currency Exchange Confirmation");
        message.setText(String.format("Exchange from %s to %s\nAmount: %s\nExchanged Amount: %s", from, to, amount, exchangedAmount));
        mailSender.send(message);
    }
}
