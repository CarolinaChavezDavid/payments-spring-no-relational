package com.payments.no.relational.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    private String id;

    private String cardNumber;
    private String cvv;
    private String cardHolderNameInCard;
    private LocalDate sinceDate;
    private LocalDate expirationDate;

    @DBRef(lazy = true)
    private Bank bank;

    @DBRef(lazy = true)
    private Customer cardHolder;

    @DBRef(lazy = true)
    @JsonBackReference
    private Set<Purchase> purchases = new HashSet<>();

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }
}
