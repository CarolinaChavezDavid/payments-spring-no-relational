package com.payments.no.relational.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "purchases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseMonthlyPayments extends Purchase {

    private float interest;

    private int numberOfQuotas;

    @DBRef
    @JsonManagedReference
    private Set<Quota> quotas = new HashSet<>();

    public void addQuota(Quota quota) {
        this.quotas.add(quota);
    }
}
