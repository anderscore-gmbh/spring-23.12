package com.anderscore.justparkit.zugangskontrolle.service;

import com.anderscore.justparkit.zugangskontrolle.client.PaymentClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class ExitGrantServiceImpl implements ExitGrantService {

    private static final Logger logger = LoggerFactory.getLogger(ExitGrantServiceImpl.class);

    @Autowired
    private PaymentClient paymentClient;

    @Override
    public boolean isExitGranted(Long ticketId) {
        try {
            return paymentClient.isPaid(ticketId);

        } catch (ResourceAccessException e){
            logger.error("Technical error while checking payment. Granting exit for ticket {}.", ticketId, e);
            return true;
        }
    }
}