package com.payments.no.relational.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class FinancingDTO {
    private String code;
    private String promotionTitle;
    private String nameStore;
    private String cuitStore;
    private LocalDate validityStartDate;
    private LocalDate validityEndDate;
    private String comments;
    private int numberOfQuotas;
    private float interest;
}
