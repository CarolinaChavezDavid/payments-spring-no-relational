package com.payments.no.relational.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDate;
import java.util.HashSet;
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

    @DBRef
    private Set<Quota> quotasPayments = new HashSet<>();

    @DBRef
    private Set<PurchaseSinglePayment> cashPayments = new HashSet<>();

    @DBRef
    private Card card;
}
