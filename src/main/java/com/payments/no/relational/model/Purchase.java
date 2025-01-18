package com.payments.no.relational.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "purchases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id
    private String id;

    private String paymentVoucher;

    private String store;

    private String cuitStore;

    private float amount;

    private float finalAmount;

    private LocalDate purchaseDate = LocalDate.now();

    @DBRef
    private Card card;

    @DBRef
    private Promotion validPromotion;
}
