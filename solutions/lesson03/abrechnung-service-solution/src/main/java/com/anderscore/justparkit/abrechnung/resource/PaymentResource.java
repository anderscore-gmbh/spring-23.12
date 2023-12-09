package com.anderscore.justparkit.abrechnung.resource;

import com.anderscore.justparkit.abrechnung.persistence.Payment;
import com.anderscore.justparkit.abrechnung.persistence.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentResource {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private PaymentToMapper mapper;

    @PostMapping
    public Long create(@RequestBody PaymentTo to){
        Payment payment = mapper.asEntity(to);

        repository.save(payment);

        return payment.getId();
    }

    @GetMapping("/{id}")
    public PaymentTo find(@PathVariable("id") Long id){
        Optional<Payment> entity = repository.findById(id);

        return entity.isPresent() ? mapper.asTo(entity.get()) : null;
    }

    @GetMapping
    public PaymentTo findByTicketId(@RequestParam("ticketId") Long ticketId){
        Optional<Payment> entity = repository.findByTicketId(ticketId);

        return entity.isPresent() ? mapper.asTo(entity.get()) : null;
    }
}