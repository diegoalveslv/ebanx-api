package com.example.ebanxapi.account.dto;

public class DestinationDTO {

    private BalanceDTO destination;

    public DestinationDTO(BalanceDTO destination) {
        this.destination = destination;
    }

    public BalanceDTO getDestination() {
        return destination;
    }

    public void setDestination(BalanceDTO destination) {
        this.destination = destination;
    }
}
