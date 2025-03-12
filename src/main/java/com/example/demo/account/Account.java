package com.example.demo.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "account",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "paypalAccountId"),
                @UniqueConstraint(columnNames = "msisdn")
        }
)
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // Ensures only non-null fields are included
public class Account {
    @Id
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_sequence"
    )
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    @Column(nullable = false, unique = true)
    private String msisdn;

    @JsonProperty
    @Column(nullable = false, unique = true)
    private String paypalAccountId;

    @JsonProperty
    @Column(nullable = false, unique = true)
    private String username;

    @JsonProperty
    private String password;

    @JsonProperty
    private double balance;

    public Account(String name, String msisdn, String paypalAccountId, String username, String password, double balance) {
        this.name = name;
        this.msisdn = msisdn;
        this.paypalAccountId = paypalAccountId;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }
}
