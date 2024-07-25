package com.example.ebanxapi.account.dto;

public class OriginDTO {

    private BalanceDTO origin;

    public OriginDTO(BalanceDTO origin) {
        this.origin = origin;
    }

    public BalanceDTO getOrigin() {
        return origin;
    }

    public void setOrigin(BalanceDTO origin) {
        this.origin = origin;
    }
}
