package com.payments.no.relational.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Set;

@Document(collection = "payment_summary")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSummary {
    @Id
    private String id;

    private String code;

    private String summaryMonth;

    private String summaryYear;

    private LocalDate firstExpiration;

    private LocalDate secondExpiration;

    private float surchargePercentage;

    private float totalPrice;

    @DBRef(lazy = true)
    private Card card;

    private Set<Quota> quotasPayments;

    private Set<PurchaseSinglePayment> cashPayments;
}

