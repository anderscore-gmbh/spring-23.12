package com.anderscore.justparkit.zugangskontrolle.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentClientImpl implements PaymentClient{

    @Value("${payment.url}")
    private String paymentUrl;

    public boolean isPaid(Long ticketId){
        String url = paymentUrl + "?ticketId=" + ticketId;
        RestTemplate restTemplate = new RestTemplate();
        PaymentTo paymentTo = restTemplate.getForObject(url, PaymentTo.class);

        return paymentTo != null && paymentTo.isCompleted();
    }
}