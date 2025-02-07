package com.payments.no.relational.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "customers")
//@Data
//@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Customer {

    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull
    private String completeName;

    @NonNull
    private String dni;

    @NonNull
    private String cuil;

    @NonNull
    private String address;

    @NonNull
    private String telephone;

    @NonNull
    private LocalDate entryDate;

    @DBRef
    @JsonBackReference
    private Set<Bank> banks = new HashSet<>();

    public void addBank(Bank bank) {
        this.banks.add(bank);
    }
}