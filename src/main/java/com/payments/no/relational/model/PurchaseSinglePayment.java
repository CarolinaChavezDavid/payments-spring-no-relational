package com.payments.no.relational.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "purchases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseSinglePayment extends Purchase {

    private float storeDiscount;

    public PurchaseSinglePayment(String paymentVoucher, String store, String cuitStore, float amount, float finalAmount,
                                 float storeDiscount){
        super(paymentVoucher, store, cuitStore, amount, finalAmount);
        this.storeDiscount = storeDiscount;
    }
}
