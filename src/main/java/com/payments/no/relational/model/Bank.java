package com.payments.no.relational.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "banks")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Bank {

    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String cuit;

    @NonNull
    private String address;

    @NonNull
    private String telephone;

    @DBRef
    private Set<Customer> members = new HashSet<>();

    @DBRef
    private Set<Promotion> promotions = new HashSet<>();

    public void addCustomer(Customer customer) {
        this.members.add(customer);
    }

    public void addPromo(Promotion promo) {
        this.promotions.add(promo);
    }
}
