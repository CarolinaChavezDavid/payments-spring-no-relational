package com.payments.no.relational.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SinglePurchaseDTO extends PurchaseDTO {
    private float storeDiscount;
}
