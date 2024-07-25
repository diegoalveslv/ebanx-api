package com.example.ebanxapi.account.dto;

public class BalanceDTO {
    private Integer id;
    private Integer balance;

    public BalanceDTO(Integer id, Integer balance) {
        this.id = id;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
