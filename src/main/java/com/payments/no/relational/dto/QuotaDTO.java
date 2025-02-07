package com.payments.no.relational.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotaDTO {

    private String id;
    private int number;
    private float price;
    private String monthh;
    private String yearr;
}
