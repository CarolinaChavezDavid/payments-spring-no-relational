package com.payments.no.relational.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "purchases")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull
    private String paymentVoucher;

    @NonNull
    private String store;

    @NonNull
    private String cuitStore;

    @NonNull
    private float amount;

    @NonNull
    private float finalAmount;

    private LocalDate purchaseDate = LocalDate.now();

    @DBRef
    private Card card;

    @DBRef
    private Promotion validPromotion;

}
