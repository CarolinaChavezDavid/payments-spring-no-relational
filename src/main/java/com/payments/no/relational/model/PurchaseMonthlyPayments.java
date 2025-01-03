package com.payments.no.relational.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "purchases") // Same collection as Purchase
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseMonthlyPayments extends Purchase {

    private float interest;

    private int numberOfQuotas;

    private Set<Quota> quotas = new HashSet<>();

    public void addQuota(Quota quota) {
        this.quotas.add(quota);
    }
}
