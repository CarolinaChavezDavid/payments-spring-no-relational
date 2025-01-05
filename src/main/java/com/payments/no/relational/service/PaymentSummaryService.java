package com.payments.no.relational.service;


import com.payments.no.relational.model.PaymentSummary;

public interface PaymentSummaryService {
    PaymentSummary getPaymentSummary(String cardId, String month, String year);
}
