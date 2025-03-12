package com.example.demo.account;

import com.example.demo.dto.request.TransactRequest;
import com.example.demo.dto.response.AccountResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;



    public Object addNewAccount(Account account) {
        Optional<Account> accountByMsisdn = accountRepository.findAccountByMsisdn((account.getMsisdn()));
        Optional<Account> accountByID = accountRepository.findAccountPaypalId((account.getPaypalAccountId()));

        if (accountByMsisdn.isPresent() || accountByID.isPresent()) {
            throw new IllegalStateException("Account taken");
        }

        accountRepository.save(account);

        AccountResponse accountResponse = new AccountResponse(
                account.getName(),
                account.getMsisdn(),
                account.getPaypalAccountId(),
                account.getBalance()
        );



        return new ApiResponse(
                "200",
                "Successfully Added",
                new Response(accountResponse)
        );
    }

    @Transactional
    public Object deleteAccount(Long accountId) {
        boolean exists = accountRepository.existsById(accountId);


        if (!exists) {
            throw new IllegalStateException(
                    "Account with id " + accountId + " not found"
            );
        } else {
            Account accountDelete = accountRepository.findById(accountId)
                    .orElseThrow(()->new IllegalStateException(
                            "Account ID: " + accountId + " does not exist"
                    ));
            accountRepository.deleteById(accountId);

            AccountResponse accountResponse = new AccountResponse(
                    accountDelete.getName(),
                    accountDelete.getMsisdn(),
                    accountDelete.getPaypalAccountId(),
                    accountDelete.getBalance()
            );



            return new ApiResponse(
                    "200",
                    "Successfully Deleted",
                    new Response(accountResponse)
            );

        }


    }

    @Transactional
    public Object updateAccount(Long accountId, Account account) {


        Account accountUpdate = accountRepository.findById(accountId)
                .orElseThrow(()->new IllegalStateException(
                        "account with id " + accountId + " does not exist"
                ));


        if (account.getName() != null && !account.getName().isEmpty() && !Objects.equals(accountUpdate.getName(), account.getName())){
            accountUpdate.setName(account.getName());
        }

        updateMsisdn(account, accountUpdate);

        updatePaypalId(account, accountUpdate);

        updatePaypalUsername(account, accountUpdate);


        if (account.getPassword() != null && !account.getPassword().isEmpty() && !Objects.equals(accountUpdate.getPassword(), account.getPassword() )){

            accountUpdate.setPassword(account.getPassword());
        }

        accountRepository.save(account);

        AccountResponse accountResponse = new AccountResponse(
                accountUpdate.getName(),
                accountUpdate.getMsisdn(),
                accountUpdate.getPaypalAccountId(),
                accountUpdate.getBalance()
        );

        return new ApiResponse(
                "200",
                "Successfully Updated",
                new Response(accountResponse)
        );


    }

    private void updatePaypalUsername(Account account, Account accountUpdate) {
        if (account.getUsername() != null && !account.getUsername().isEmpty() && !Objects.equals(accountUpdate.getUsername(), account.getUsername() )){
            Optional<Account> accountOptional = accountRepository.findAccountUsername(account.getUsername());

            if (accountOptional.isPresent()){
                throw new IllegalStateException("Username taken");
            }
            accountUpdate.setUsername(account.getUsername());
        }
    }

    private void updatePaypalId(Account account, Account accountUpdate) {
        if (account.getPaypalAccountId() != null && !account.getPaypalAccountId().isEmpty() && !Objects.equals(accountUpdate.getPaypalAccountId(), account.getPaypalAccountId() )){
            Optional<Account> accountOptional = accountRepository.findAccountPaypalId(account.getPaypalAccountId());

            if (accountOptional.isPresent()){
                throw new IllegalStateException("Account ID taken");
            }
            accountUpdate.setPaypalAccountId(account.getPaypalAccountId());
        }
    }

    private void updateMsisdn(Account account, Account accountUpdate) {
        if (account.getMsisdn() != null && !Objects.equals(accountUpdate.getMsisdn() , account.getMsisdn())){
            Optional<Account> accountOptional = accountRepository.findAccountByMsisdn(account.getMsisdn());
            if (accountOptional.isPresent()){
                throw new IllegalStateException("Msisdn is taken");
            }
            accountUpdate.setMsisdn(account.getMsisdn());
        }
    }


    @Transactional
    public Object getAccount(String msisdn) {

        Account accountGet = accountRepository.findByMsisdn(msisdn)
                .orElseThrow(()->new IllegalStateException(
                        "account with msisdn " + msisdn + " does not exist"
                ));

        AccountResponse accountResponse = new AccountResponse(
                accountGet.getId(),
                accountGet.getName(),
                accountGet.getMsisdn(),
                accountGet.getPaypalAccountId(),
                accountGet.getBalance()
        );


        return new ApiResponse(
                "200",
                "Account Exists",
                new Response(accountResponse)
        );

    }

    public Object getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();



        List<AccountResponse> accountResponses = new ArrayList<>();

        for (Account account : accounts) {
            AccountResponse accountResponse = new AccountResponse();

            accountResponse.setMsisdn(account.getMsisdn());
            accountResponse.setPaypalAccountId(account.getPaypalAccountId());
            accountResponse.setName(account.getName());
            accountResponse.setBalance(account.getBalance());

            accountResponses.add(accountResponse);

        }



        return new ApiListResponse(
                "200",
                "Success",
                accounts.size(),
                accountResponses
        );

    }

    public Object transact(TransactRequest transactRequest, Long accountId) throws IllegalAccessException {

        Account account = accountRepository.findByIdAndUsernameAndPassword (accountId,transactRequest.getPaypalUsername(), transactRequest.getPaypalPassword());

        if (account == null) {
            throw new IllegalAccessException("Invalid Credentials");
        }


        if(account.getBalance() > transactRequest.getAmount()){
            account.setBalance(account.getBalance() - transactRequest.getAmount());
        } else {
            throw new IllegalStateException("Insufficient funds");
        }
        accountRepository.save(account);

        AccountResponse accountResponse = new AccountResponse(
                account.getName(),
                account.getMsisdn(),
                account.getPaypalAccountId(),
                account.getBalance()
        );




        return new ApiResponse(
                "200",
                "Transaction Successful: TID :"+transactRequest.getRequestRefId(),
                new Response(accountResponse)
        );

    }


    record Response (AccountResponse account){

    }

    record ApiResponse(
            String responseCode,
            String responseMessage,
            Response result

    ){}


    record ApiListResponse(
            String responseCode,
            String responseMessage,
            int records,
            List<AccountResponse> accounts

    ){}

}
