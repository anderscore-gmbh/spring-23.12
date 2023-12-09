package com.anderscore.justparkit.zugangskontrolle.client;

public interface PaymentClient {

    boolean isPaid(Long ticketId);
}
