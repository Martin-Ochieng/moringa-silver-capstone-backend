package com.example.demo.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    @Query("SELECT s FROM Account  s Where s.msisdn=?1")
    Optional<Account> findAccountByMsisdn(String msisdn);

    @Query(""+
    "SELECT CASE WHEN COUNT (s) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Account s " +
            "WHERE s.msisdn = ?1"
    )
    Boolean selectExistsMsisdn(String msisdn);



    @Query("SELECT s FROM Account  s Where s.paypalAccountId=?1")
    Optional<Account> findAccountPaypalId(String paypalAccountId);

    @Query(""+
            "SELECT CASE WHEN COUNT (s) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Account s " +
            "WHERE s.paypalAccountId = ?1"
    )
    Boolean selectExistsPaypalId(String paypalAccountId);



    @Query("SELECT s FROM Account  s Where s.username=?1")
    Optional<Account> findAccountUsername(String username);


    Account findByIdAndUsernameAndPassword(Long accountId, String paypalUsername, String paypalPassword);

    Optional<Account> findByMsisdn(String msisdn);
}
