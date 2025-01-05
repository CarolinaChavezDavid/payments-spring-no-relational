package com.payments.no.relational.controller;

import com.payments.no.relational.model.Bank;
import com.payments.no.relational.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banking")
public class BankController {

    Logger logger = LoggerFactory.getLogger(BankController.class);

    @Autowired
    private BankService bankService;

    @GetMapping
    public ResponseEntity<List<Bank>> getAllBanks() {
        return ResponseEntity.ok().body(bankService.getAllBanks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBankById(@PathVariable String id) {
        try {
            Bank bank = bankService.getBankById(id);
            return ResponseEntity.ok().body(bank);
        } catch (Exception e) {
            logger.error("There's no bank associated to the id provided", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/customers/{customerId}/banks/{bankId}")
    public ResponseEntity<Bank> addCustomerToBank(@PathVariable String customerId, @PathVariable String bankId) {
        try {
            Bank updatedBank = bankService.addCustomerToBank(customerId, bankId);
            return ResponseEntity.ok(updatedBank);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
