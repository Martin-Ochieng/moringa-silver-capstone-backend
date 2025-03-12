package com.example.demo.account;

import com.example.demo.dto.request.AccountRequest;
import com.example.demo.dto.request.TransactRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/account")
@Tag(name = "Accounts", description = "APIs for managing accounts")
public class AccountController {

    private final AccountService accountService;



    @GetMapping()
    public Object getAllAccounts() {return accountService.getAllAccounts();}

    @PostMapping
    public Object registerNewAccount(@RequestBody Account account){
        return accountService.addNewAccount(account);
    }

    @DeleteMapping(path  = "{accountId}")
    public Object deleteAccount(@PathVariable("accountId") Long accountId){
        return accountService.deleteAccount(accountId);
    }

    @GetMapping(path =  "{msisdn}")
    public Object getAccount(@PathVariable("msisdn")String msisdn) {return accountService.getAccount(msisdn);}

    @PutMapping(path = "{accountId}")
    public Object updateAccount(
            @PathVariable("accountId") Long accountId,
            @RequestBody AccountRequest account) {
        return accountService.updateAccount(accountId, account);

    }


    @PostMapping(path = "/transact/{accountId}")
    public Object transact(@RequestBody TransactRequest transactRequest, @PathVariable("accountId")Long accountId) throws IllegalAccessException {
        return accountService.transact(transactRequest, accountId);
    }

}
