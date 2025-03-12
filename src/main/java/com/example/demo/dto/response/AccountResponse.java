package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // Ensures only non-null fields are included
public class AccountResponse {
    public AccountResponse(String name, String msisdn, String paypalAccountId, double balance) {
        this.name = name;
        this.msisdn = msisdn;
        this.paypalAccountId = paypalAccountId;
        this.balance = balance;
    }

    @JsonProperty
    private long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String msisdn;

    @JsonProperty
    private String paypalAccountId;

    @JsonProperty
    private double balance;

}
