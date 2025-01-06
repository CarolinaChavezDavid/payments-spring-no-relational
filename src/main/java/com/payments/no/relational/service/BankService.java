package com.payments.no.relational.service;

import com.payments.no.relational.dto.BankCustomerDTO;
import com.payments.no.relational.model.Bank;

import java.util.List;

public interface BankService {
    List<Bank> getAllBanks();
    Bank getBankById(String id);
    Bank saveBank(Bank bank);
    Bank addCustomerToBank(String customerId, String bankId);
    List<BankCustomerDTO> getCustomersAmountPerBank();
}
