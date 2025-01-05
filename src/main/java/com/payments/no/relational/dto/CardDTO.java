package com.payments.no.relational.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CardDTO {
    private String cardNumber;
    private String cvv;
    private String cardHolderNameInCard;
    private LocalDate sinceDate;
    private LocalDate expirationDate;
    private Long bankId;
    private Long customerId;
}
