package com.payments.no.relational.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "promotions")
@Data
@NoArgsConstructor
public class Financing extends Promotion {

    private int numberOfQuotas;

    private float interest;
}