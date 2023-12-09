package com.anderscore.justparkit.abrechnung.persistence;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(name = "ticket_id")
    @NotNull
    private Long ticketId;

    @NotNull
    private Boolean completed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (!id.equals(payment.id)) return false;
        if (!ticketId.equals(payment.ticketId)) return false;
        return completed.equals(payment.completed);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + ticketId.hashCode();
        result = 31 * result + completed.hashCode();
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}