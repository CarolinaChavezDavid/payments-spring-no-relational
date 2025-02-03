package com.payments.no.relational.service;

import com.payments.no.relational.dto.MonthlyPaymentPurchaseDTO;
import com.payments.no.relational.dto.PurchaseProjection;
import com.payments.no.relational.dto.SinglePurchaseDTO;
import com.payments.no.relational.dto.TopStoreDTO;
import com.payments.no.relational.model.Purchase;
import com.payments.no.relational.model.PurchaseMonthlyPayments;
import com.payments.no.relational.model.PurchaseSinglePayment;

import java.util.List;
import java.util.Map;

public interface PurchaseService {
    List<Purchase> getAllPurchases();
    PurchaseProjection getPurchaseById(String id);
    Purchase createPurchase(Purchase purchase);
    PurchaseMonthlyPayments createPurchaseMonthlyPayments(MonthlyPaymentPurchaseDTO monthlyPurchase);
    PurchaseSinglePayment createPurchaseSinglePayment(SinglePurchaseDTO singlePurchase);
    TopStoreDTO getTopStore(int month, int year);
}
