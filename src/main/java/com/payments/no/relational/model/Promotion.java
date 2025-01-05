package com.payments.no.relational.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "promotions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Promotion {

    @Id
    private String id;

    private String code;

    private String promotionTitle;

    private String nameStore;

    private String cuitStore;

    private LocalDate validityStartDate;

    private LocalDate validityEndDate;

    private String comments;

    @DBRef
    private Set<Purchase> purchases = new HashSet<>();

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }
}
