package com.payments.no.relational.controller;


import com.payments.no.relational.dto.MonthlyPaymentPurchaseDTO;
import com.payments.no.relational.dto.PurchaseProjection;
import com.payments.no.relational.dto.SinglePurchaseDTO;
import com.payments.no.relational.dto.TopStoreDTO;
import com.payments.no.relational.model.Purchase;
import com.payments.no.relational.model.PurchaseMonthlyPayments;
import com.payments.no.relational.model.PurchaseSinglePayment;
import com.payments.no.relational.service.PurchaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    // 5) Obtener la informacion de una compra, incluyendo el listado de cuotas si esta posee
    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable String id) {
        try {
            Purchase purchase = purchaseService.getPurchaseById(id);
            return ResponseEntity.ok(purchase);
        }catch (Exception e){
            logger.error("There was a error getting the purchase information", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        List<Purchase> purchases = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchases);
    }

    @PostMapping("/single")
    public ResponseEntity<PurchaseSinglePayment> createSinglePurchase(@RequestBody SinglePurchaseDTO singlePurchase) {
        try {
            PurchaseSinglePayment savedPurchase = purchaseService.createPurchaseSinglePayment(singlePurchase);
            return ResponseEntity.ok(savedPurchase);
        }catch (Exception e){
            logger.error("There was a error saving the purchase information", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/monthly")
    public ResponseEntity<PurchaseMonthlyPayments> createMonthlyPurchase(@RequestBody MonthlyPaymentPurchaseDTO monthlyPurchase) {
        try {
            PurchaseMonthlyPayments savedPurchase = purchaseService.createPurchaseMonthlyPayments(monthlyPurchase);
            return ResponseEntity.ok(savedPurchase);
        }catch (Exception e){
            logger.error("There was a error saving the purchase information", e);
            return ResponseEntity.badRequest().build();
        }
    }

    // 10) Obtener el nombre y el CUIT del local que mas facturo en cierto mes
    @GetMapping("/top-income-store")
    public TopStoreDTO getTopIncomeStore(
            @RequestParam int month,
            @RequestParam int year
    ) {
        TopStoreDTO res = purchaseService.getTopStore(month, year);
        return res;
    }
}
