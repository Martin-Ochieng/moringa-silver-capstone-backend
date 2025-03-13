package com.example.demo.account;

import com.example.demo.dto.request.AccountRequest;
import com.example.demo.dto.request.TransactRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/account")
@Tag(name = "Accounts", description = "Endpoints for managing user accounts, including creation, retrieval, updates, and transactions.")
public class AccountController {

    private final AccountService accountService;

    @GetMapping()
    @Operation(summary = "Retrieve all accounts", description = "Fetches a list of all registered accounts.")
    public Object getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping
    @Operation(summary = "Create a new account", description = "Registers a new account with the provided details.")
    public Object registerNewAccount(@RequestBody AccountRequest account) {
        return accountService.addNewAccount(account);
    }

    @DeleteMapping(path = "{accountId}")
    @Operation(summary = "Delete an account", description = "Removes an account from the system using its unique ID.")
    public Object deleteAccount(@PathVariable("accountId") Long accountId) {
        return accountService.deleteAccount(accountId);
    }

    @GetMapping(path = "{msisdn}")
    @Operation(summary = "Get account by MSISDN", description = "Retrieves account details associated with the given MSISDN.")
    public Object getAccount(@PathVariable("msisdn") String msisdn) {
        return accountService.getAccount(msisdn);
    }

    @PutMapping(path = "{accountId}")
    @Operation(summary = "Update account details", description = "Updates the details of an existing account using its unique ID.")
    public Object updateAccount(
            @PathVariable("accountId") Long accountId,
            @RequestBody AccountRequest account) {
        return accountService.updateAccount(accountId, account);
    }

    @PostMapping(path = "/transact/{accountId}")
    @Operation(summary = "Initiate a transaction", description = "Processes a withdrawal transaction from PayPal to Mpesa for the specified account.")
    public Object transact(@RequestBody TransactRequest transactRequest, @PathVariable("accountId") Long accountId) throws IllegalAccessException {
        return accountService.transact(transactRequest, accountId);
    }
}
