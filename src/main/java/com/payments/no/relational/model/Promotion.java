package com.payments.no.relational.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "promotions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Promotion {

    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull
    private String code;

    @NonNull
    private String promotionTitle;

    @NonNull
    private String nameStore;

    @NonNull
    private String cuitStore;

    @NonNull
    private LocalDate validityStartDate;

    @NonNull
    private LocalDate validityEndDate;

    private String comments;

    @DBRef
    @JsonBackReference
    private List<Purchase> purchases = new ArrayList<>();

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }
}
