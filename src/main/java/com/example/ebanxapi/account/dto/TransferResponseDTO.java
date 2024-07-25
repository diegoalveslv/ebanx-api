package com.example.ebanxapi.account.dto;

public class TransferResponseDTO {

    private BalanceDTO origin;
    private BalanceDTO destination;

    public TransferResponseDTO(BalanceDTO origin, BalanceDTO destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public BalanceDTO getOrigin() {
        return origin;
    }

    public void setOrigin(BalanceDTO origin) {
        this.origin = origin;
    }

    public BalanceDTO getDestination() {
        return destination;
    }

    public void setDestination(BalanceDTO destination) {
        this.destination = destination;
    }
}
