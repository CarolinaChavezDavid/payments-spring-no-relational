package com.payments.no.relational.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "promotions")
@Data
@NoArgsConstructor
public class Discount extends Promotion {

    private float discountPercentage;

    private float priceCap;

    private boolean onlyCash;
}

