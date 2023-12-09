package com.anderscore.justparkit.abrechnung.resource;

import com.anderscore.justparkit.abrechnung.persistence.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentToMapperImpl implements PaymentToMapper {

    @Override
    public PaymentTo asTo(Payment entity) {
        PaymentTo to = new PaymentTo();
        to.setPaymentId(entity.getId());
        to.setTicketId(entity.getTicketId());
        to.setCompleted(entity.getCompleted());

        return to;
    }

    @Override
    public Payment asEntity(PaymentTo to) {
        Payment entity = new Payment();
        entity.setTicketId(to.getTicketId());
        entity.setCompleted(to.isCompleted());

        return entity;
    }
}