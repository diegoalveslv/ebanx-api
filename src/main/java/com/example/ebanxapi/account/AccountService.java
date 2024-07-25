package com.example.ebanxapi.account;

import com.example.ebanxapi.account.dto.BalanceDTO;
import com.example.ebanxapi.account.dto.TransferResponseDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

    private final Map<Integer, Account> accountByAccountId = new HashMap<>();

    public Integer getBalance(Integer accountId) {
        Account account = accountByAccountId.get(accountId);

        return account != null ? account.getBalance() : null;
    }

    public Account deposit(Integer destination, Integer amount) {
        Account account = accountByAccountId.get(destination);
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
        } else {
            account = new Account(destination, amount);
            accountByAccountId.put(destination, account);
        }

        return account;
    }

    public Account withdraw(Integer origin, Integer amount) throws AccountNotFoundException {
        Account account = accountByAccountId.get(origin);

        if (account == null) {
            throw new AccountNotFoundException("Origin account with id %s not found".formatted(origin));
        } else {
            account.setBalance(account.getBalance() - amount);
            return account;
        }
    }

    //this lacks transactions
    public TransferResponseDTO transfer(Integer origin, Integer amount, Integer destination) throws AccountNotFoundException {
        Account originAccount = accountByAccountId.get(origin);
        if (originAccount == null) {
            throw new AccountNotFoundException("Origin account with id %s not found".formatted(origin));
        }

        originAccount.setBalance(originAccount.getBalance() - amount);

        Account destinationAccount = accountByAccountId.computeIfAbsent(destination, d -> new Account(d, 0));
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        var originAccountBalance = new BalanceDTO(originAccount.getId(), originAccount.getBalance());
        var destinationAccountBalance = new BalanceDTO(destinationAccount.getId(), destinationAccount.getBalance());
        return new TransferResponseDTO(originAccountBalance, destinationAccountBalance);
    }

    public void reset() {
        accountByAccountId.clear();
    }
}
