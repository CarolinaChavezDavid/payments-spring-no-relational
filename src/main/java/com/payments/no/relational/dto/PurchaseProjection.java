package com.payments.no.relational.dto;

public interface PurchaseProjection {
    String getPaymentVoucher();
    String getStore();
    String getCuitStore();
    String getAmount();
    String getFinalAmount();
    String getPurchaseDate();
}
