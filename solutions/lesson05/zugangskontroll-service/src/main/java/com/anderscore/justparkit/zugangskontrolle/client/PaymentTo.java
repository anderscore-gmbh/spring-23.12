package com.anderscore.justparkit.zugangskontrolle.client;

public class PaymentTo {

    private long ticketId;
    private boolean completed;

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}