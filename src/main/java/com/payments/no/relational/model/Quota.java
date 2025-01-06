package com.payments.no.relational.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quota {
    private int number;
    private float price;
    private String monthh;
    private String yearr;

    @DBRef
    @JsonBackReference
    private Purchase purchase;
}