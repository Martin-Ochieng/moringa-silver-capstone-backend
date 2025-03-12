package com.example.demo.account;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class AccountConfig {

    @Bean
    CommandLineRunner commandLineRunner(AccountRepository repository){
        return args -> {

            Account mariam = new Account(
                    "Miriam Makeba",
                    "254798881619",
                    "1",
                    "miriam",
                    "evans@123",
                    20.0

            );

            Account martin = new Account(
                    "Martin Papa",
                    "254798881618",
                    "2",
                    "Martin",
                    "evans@123",
                    20.0

            );





            repository.saveAll(
                    List.of(mariam,martin)
            );
        };
    }
}
