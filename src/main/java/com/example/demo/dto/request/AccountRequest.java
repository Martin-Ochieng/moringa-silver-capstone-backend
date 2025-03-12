package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // Ensures only non-null fields are included
public class AccountRequest {

    @JsonProperty
    private String name;

    @JsonProperty
    private String msisdn;

    @JsonProperty
    private String paypalAccountId;

    @JsonProperty
    private String username;

    @JsonProperty
    private String password;

    @JsonProperty
    private double balance;

}
