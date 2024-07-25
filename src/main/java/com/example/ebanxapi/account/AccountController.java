package com.example.ebanxapi.account;

import com.example.ebanxapi.account.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestParam(name = "account_id") Integer accountId) {
        Integer accountBalance = accountService.getBalance(accountId);

        if (accountBalance != null) {
            return ResponseEntity.ok(accountBalance);
        } else {
            return new ResponseEntity<>("0", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/event")
    public ResponseEntity<?> processEvent(@RequestBody EventRequestDTO request) {
        Account account;
        if (AccountEventType.deposit.equals(request.getType())) {
            account = accountService.deposit(request.getDestination(), request.getAmount());

            var responseBody = new DestinationDTO(new BalanceDTO(account.getId(), account.getBalance()));
            return ResponseEntity.status(HttpStatus.CREATED.value()).body(responseBody);
        } else if (AccountEventType.withdraw.equals(request.getType())) {
            account = accountService.withdraw(request.getOrigin(), request.getAmount());

            var responseBody = new OriginDTO(new BalanceDTO(account.getId(), account.getBalance()));
            return ResponseEntity.status(HttpStatus.CREATED.value()).body(responseBody);
        } else if (AccountEventType.transfer.equals(request.getType())) {
            TransferResponseDTO response = accountService.transfer(request.getOrigin(), request.getAmount(), request.getDestination());

            return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).build();
    }

    @PostMapping("/reset")
    public ResponseEntity<?> reset() {
        accountService.reset();

        return ResponseEntity.ok("OK");
    }
}
