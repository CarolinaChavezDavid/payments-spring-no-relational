package com.payments.no.relational.dto;

import lombok.Data;

@Data
public class BankCustomerDTO {
    private String bankId;
    private String bank_name;
    private int members_amount;
}
