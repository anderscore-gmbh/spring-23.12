package com.anderscore.justparkit.abrechnung.resource;

import com.anderscore.justparkit.abrechnung.persistence.Payment;

public interface PaymentToMapper {

    PaymentTo asTo(Payment entity);

    Payment asEntity(PaymentTo to);
}