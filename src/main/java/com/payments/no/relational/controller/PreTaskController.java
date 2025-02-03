package com.payments.no.relational.controller;

import com.payments.no.relational.dto.PreTaskDTO;
import com.payments.no.relational.service.PreTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pretask")
public class PreTaskController {
    private final PreTaskService preTaskService;

    public PreTaskController(PreTaskService preTaskService) {
        this.preTaskService = preTaskService;
    }

    @GetMapping
    public ResponseEntity<PreTaskDTO> getVariables(
            @RequestParam String bank_name,
            @RequestParam String promotion_code,
            @RequestParam String card_number,
            @RequestParam String promotion_delete_code,
            @RequestParam String purchase_payment_voucher

    ) {
        PreTaskDTO pretask = preTaskService.getPreTaskVariables(
                bank_name,
                promotion_code,
                card_number,
                promotion_delete_code,
                purchase_payment_voucher
        );
        return ResponseEntity.ok(pretask);
    }

}
