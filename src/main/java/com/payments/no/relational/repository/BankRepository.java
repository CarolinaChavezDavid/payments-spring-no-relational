package com.payments.no.relational.repository;

import com.payments.no.relational.model.Bank;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BankRepository  extends MongoRepository<Bank, String> {
}
