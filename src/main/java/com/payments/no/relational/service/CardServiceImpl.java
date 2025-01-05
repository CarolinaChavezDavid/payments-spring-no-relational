package com.payments.no.relational.service;

import com.payments.no.relational.dto.CardDTO;
import com.payments.no.relational.exception.PaymentsException;
import com.payments.no.relational.model.Bank;
import com.payments.no.relational.model.Card;
import com.payments.no.relational.repository.BankRepository;
import com.payments.no.relational.repository.CardRepository;
import com.payments.no.relational.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService{

    private final CardRepository cardRepository;
    private final BankRepository bankRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, BankRepository bankRepository, CustomerRepository customerRepository) {
        this.cardRepository = cardRepository;
        this.bankRepository = bankRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public Optional<Card> getCardById(String id) {
        return cardRepository.findById(id);
    }

    @Override
    public Card createCard(CardDTO cardDTO) {
        Bank bank =  bankRepository
                .findById(cardDTO.getBankId().toString())
                .orElseThrow(() -> new PaymentsException("Bank with ID " + cardDTO.getBankId() + " not found"));

        Card card = new Card();
        card.setCardNumber(cardDTO.getCardNumber());
        card.setCvv(cardDTO.getCvv());
        card.setCardHolderNameInCard(cardDTO.getCardHolderNameInCard());
        card.setSinceDate(cardDTO.getSinceDate());
        card.setExpirationDate(cardDTO.getExpirationDate());
        card.setBank(bank);
        return cardRepository.save(card);
    }

    @Override
    public List<Card> getCardsCloseToExpiry() {
        LocalDate currentDate = LocalDate.now();
        LocalDate nextMonth = currentDate.plusMonths(1);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String currentDateStr = currentDate.format(formatter);
        String nextMonthStr = nextMonth.format(formatter);

        return cardRepository.findByExpirationDateBetween(currentDateStr, nextMonthStr);
    }

    @Override
    public List<Card> getTop10CardsWithMostPurchases() {
        Pageable pageable = PageRequest.of(0, 10);
        return cardRepository.findTop10CardsWithMostPurchases(0, 10);
    }
}
