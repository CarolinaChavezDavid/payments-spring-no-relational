package com.payments.no.relational.repository;

import com.payments.no.relational.model.Bank;
import com.payments.no.relational.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Set;

public interface BankRepository  extends MongoRepository<Bank, String> {

    @Query(value = "{ '_id': ?0 }", fields = "{ 'members': 1 }")
    Set<Customer> findMembersByBankId(String bankId);
}
