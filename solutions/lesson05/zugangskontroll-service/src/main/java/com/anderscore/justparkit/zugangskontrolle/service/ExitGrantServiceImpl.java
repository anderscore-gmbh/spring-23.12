package com.anderscore.justparkit.zugangskontrolle.service;

import com.anderscore.justparkit.zugangskontrolle.client.PaymentClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExitGrantServiceImpl implements ExitGrantService {

    private static final Logger logger = LoggerFactory.getLogger(ExitGrantServiceImpl.class);

    @Autowired
    private PaymentClient paymentClient;

    @CircuitBreaker(name = "exitGrantCircuitBreaker", fallbackMethod = "fallback")
    public boolean isExitGranted(Long ticketId) {
        return paymentClient.isPaid(ticketId);
    }

    boolean fallback(Long ticketId, Throwable t){
        logger.error("Technical error while checking payment. Granting exit for ticket {}.", ticketId, t);
        return true;
    }
}