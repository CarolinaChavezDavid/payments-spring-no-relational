package com.payments.no.relational.service;

import com.payments.no.relational.dto.BankCustomerDTO;
import com.payments.no.relational.exception.PaymentsException;
import com.payments.no.relational.model.Bank;
import com.payments.no.relational.model.Customer;
import com.payments.no.relational.repository.BankRepository;
import com.payments.no.relational.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final CustomerRepository customerRepository;

    Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);

    @Autowired
    public BankServiceImpl(
            BankRepository bankRepository,
            CustomerRepository customerRepository
    ) {
        this.bankRepository = bankRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public Bank getBankById(String id) {
        Optional<Bank> bankOptional = bankRepository.findById(id);
        if(bankOptional.isPresent()) {
            return bankOptional.get();
        } else {
            throw new PaymentsException("There was an error fetching the bank's information");
        }
    }

    @Override
    public Bank saveBank(Bank bank) {
        return bankRepository.save(bank);
    }

    @Override
    public Bank addCustomerToBank(String customerId, String bankId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        Optional<Bank> bankOptional = bankRepository.findById(bankId);
        if (customerOptional.isPresent() && bankOptional.isPresent()) {
            Customer customer = customerOptional.get();
            Bank bank = bankOptional.get();
            if (customer.getBanks().contains(bank)) {
                throw new PaymentsException("The customer is already associated with this bank");
            } else {
                bank.addCustomer(customer);
            }
            return bankRepository.save(bank);
        } else {
            throw new PaymentsException("There was an error adding the client to the bank");
        }
    }

    @Override
    public List<BankCustomerDTO> getCustomersAmountPerBank() {
        return bankRepository.findAll().stream().map(bank -> {
            BankCustomerDTO dto = new BankCustomerDTO();
            String bankId = bank.getId();

            Bank currentBank =  bankRepository
                    .findById(bankId)
                    .orElseThrow(() -> new PaymentsException("Bank with ID " +bankId + " not found"));

            int members = currentBank.getMembers().size();
            dto.setBankId(bankId);
            dto.setBank_name(bank.getName());
            dto.setMembers_amount(members);
            return dto;
        }).collect(Collectors.toList());
    }
}
