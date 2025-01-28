package com.payments.no.relational.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Quota {

    @Id
    private String id;

    @NonNull
    private int number;

    @NonNull
    private float price;

    @NonNull
    private String monthh;

    @NonNull
    private String yearr;

    @DBRef
    private Purchase purchase;
}