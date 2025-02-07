package com.payments.no.relational.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "purchases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseMonthlyPayments extends Purchase {

    private float interest;

    private int numberOfQuotas;

    private String type;

    @JsonManagedReference
    @Field("quotas")
    private Set<Quota> quotas = new HashSet<>();

    public PurchaseMonthlyPayments(String paymentVoucher, String store, String cuitStore, float amount, float finalAmount,
                                 float interest, int numberOfQuotas){
        super(paymentVoucher, store, cuitStore, amount, finalAmount);
        this.interest = interest;
        this.numberOfQuotas = numberOfQuotas;
        this.type = "PurchaseMonthlyPayments";
    }

    public void addQuota(Quota quota) {
        this.quotas.add(quota);
    }
}
