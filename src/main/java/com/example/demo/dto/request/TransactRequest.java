package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactRequest {
    @JsonProperty
    private String requestRefId;
    @JsonProperty
    private String paypalUsername;
    @JsonProperty
    private String paypalPassword;
    @JsonProperty
    @Pattern(regexp ="((254)|0)\\d{9}" , message = "MSISDN INVALID")
    private String msisdn;
    @JsonProperty
    private double amount;
}
