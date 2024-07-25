package com.example.ebanxapi.account.dto;

import com.example.ebanxapi.account.AccountEventType;

public class EventRequestDTO {

    private AccountEventType type;
    private Integer destination;
    private Integer origin;
    private Integer amount;

    public EventRequestDTO() {
    }

    public EventRequestDTO(AccountEventType type, Integer destination, Integer origin, Integer amount) {
        this.type = type;
        this.destination = destination;
        this.origin = origin;
        this.amount = amount;
    }

    public AccountEventType getType() {
        return type;
    }

    public void setType(AccountEventType type) {
        this.type = type;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }
}
