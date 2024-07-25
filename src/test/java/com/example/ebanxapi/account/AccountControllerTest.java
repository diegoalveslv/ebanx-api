package com.example.ebanxapi.account;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    void setup() {
        accountService.reset();
    }

    @Test
    public void givenNonExistingAccount_whenGetBalance_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/balance?account_id=1234"))
                .andExpect(status().is(404))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("0"));
    }

    @Test
    public void givenNoAccount_whenDepositEvent_shouldCreateAccount() throws Exception {
        mockMvc.perform(post("/event")
                        .content("{\"type\":\"deposit\", \"destination\":\"100\", \"amount\":10}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.destination").isNotEmpty())
                .andExpect(jsonPath("$.destination.id").value(100))
                .andExpect(jsonPath("$.destination.balance").value(10));
    }

    @Test
    public void givenExistingAccount_whenDepositEvent_shouldIncreaseBalance() throws Exception {
        accountService.deposit(100, 10); // create account

        mockMvc.perform(post("/event")
                        .content("{\"type\":\"deposit\", \"destination\":\"100\", \"amount\":10}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.destination").isNotEmpty())
                .andExpect(jsonPath("$.destination.id").value(100))
                .andExpect(jsonPath("$.destination.balance").value(20));
    }

    @Test
    public void givenExistingAccount_whenGetBalance_shouldReturnOkAndBalance() throws Exception {
        accountService.deposit(100, 20); // create account

        mockMvc.perform(get("/balance?account_id=100"))
                .andExpect(status().is(200))
                .andExpect(content().string("20"));
    }

    @Test
    public void givenNonExistingAccount_whenWithdrawEvent_shouldReturnNotFound() throws Exception {
        mockMvc.perform(post("/event")
                        .content("{\"type\":\"withdraw\", \"origin\":\"200\", \"amount\":10}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("0"));;
    }

    @Test
    public void givenExistingAccount_whenWithdrawEvent_shouldWithdrawnAndReturnBalance() throws Exception {
        accountService.deposit(100, 20); // create account

        mockMvc.perform(post("/event")
                        .content("{\"type\":\"withdraw\", \"origin\":\"100\", \"amount\":5}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.origin").isNotEmpty())
                .andExpect(jsonPath("$.origin.balance").value(15))
                .andExpect(jsonPath("$.origin.id").value(100));
    }

    @Test
    public void givenExistingAccount_whenTransferEvent_thenUpdateAccountsAndReturnBalance() throws Exception {
        accountService.deposit(100, 15); // create account

        mockMvc.perform(post("/event")
                        .content("{\"type\":\"transfer\", \"origin\":\"100\", \"amount\":15, \"destination\":\"300\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.origin").isNotEmpty())
                .andExpect(jsonPath("$.origin.id").value(100))
                .andExpect(jsonPath("$.origin.balance").value(0))
                .andExpect(jsonPath("$.destination.id").value(300))
                .andExpect(jsonPath("$.destination.balance").value(15));
    }

    @Test
    public void givenNonExistingAccount_whenTransferEvent_thenReturnNotFound() throws Exception {
        mockMvc.perform(post("/event")
                        .content("{\"type\":\"transfer\", \"origin\":\"200\", \"amount\":15, \"destination\":\"300\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("0"));;
    }

    @Test
    public void resetEndpoint_shouldResetState() throws Exception {
        int accountId = 100;
        accountService.deposit(accountId, 10); // create account

        mockMvc.perform(post("/reset"))
                .andExpect(status().is(200))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("OK"));

        Integer balance = accountService.getBalance(accountId);
        assertThat(balance).isNull();
    }
}
