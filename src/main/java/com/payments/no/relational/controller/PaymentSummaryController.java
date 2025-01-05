package com.payments.no.relational.controller;

import com.payments.no.relational.model.PaymentSummary;
import com.payments.no.relational.service.PaymentSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paymentSummary")
public class PaymentSummaryController {

    Logger logger = LoggerFactory.getLogger(PaymentSummaryController.class);

    private PaymentSummaryService paymentSummaryService;

    @Autowired
    public PaymentSummaryController(PaymentSummaryService paymentSummaryService) { this.paymentSummaryService = paymentSummaryService; }

    // 3) Generar el total de pago de un mes dado, informando las compras correspondientes
    @GetMapping
    public ResponseEntity<PaymentSummary> getPaymentSummary(
            @RequestParam String cardId,
            @RequestParam String month,
            @RequestParam String year
    ) {
        try {
            PaymentSummary paymentSummary = paymentSummaryService.getPaymentSummary(cardId, month, year);
            return ResponseEntity.ok(paymentSummary);
        }catch (Exception e){
            logger.error("There was a error getting the payment summary information", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
