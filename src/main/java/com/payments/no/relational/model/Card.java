package com.payments.no.relational.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "cards")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull
    private String cardNumber;

    @NonNull
    private String cvv;

    @NonNull
    private String cardHolderNameInCard;

    @NonNull
    private LocalDate sinceDate;

    @NonNull
    private LocalDate expirationDate;

    @DBRef(lazy = true)
    @NonNull
    @JsonIgnore
    private Bank bank;

    @DBRef(lazy = true)
    @NonNull
    @JsonIgnore
    private Customer cardHolder;

    @DBRef(lazy = true)
    @JsonBackReference
    private Set<Purchase> purchases = new HashSet<>();

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }
}
