package com.payments.no.relational;

import com.payments.no.relational.model.*;
import com.payments.no.relational.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentSummaryRepository paymentSummaryRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseMonthlyPaymentRepository purchaseMonthlyPaymentsRepository;

    @Autowired
    private QuotaRepository quotaRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){

        bankRepository.deleteAll();
        cardRepository.deleteAll();
        customerRepository.deleteAll();
        promotionRepository.deleteAll();
        purchaseRepository.deleteAll();
        quotaRepository.deleteAll();


        // 1) Agregar una promocion de tipo Financing a un banco dado
        Bank bank1 = new Bank("BBVA","30654321987","Av. Córdoba 1200","1143325680");
        bankRepository.save(bank1);

        // 2) Extender el tiempo de validez de una promocion
        Promotion promo10 = new Financing("F001","Back-to-School Financing","Store A","20-12345678-1", LocalDate.of(2024,1,1), LocalDate.of(2024,12,1),2,0.0f);
        Promotion promo11 = new Discount("D001","Travel Discount","Store B","20-12345678-2", LocalDate.of(2024,1,1), LocalDate.of(2024,12,1),30.0f,100.0f,false);
        promotionRepository.save(promo10);
        promotionRepository.save(promo11);

        // 3) Generar el total de pago de un mes dado, informando las compras correspondientes
        Customer customer1 = new Customer("John Doe","12345678","11-12345678-1","Calle 452","1234567",LocalDate.of(2024,1,1));
        customerRepository.save(customer1);
        Card card1 = new Card("1234567812345678","123","John Doe",LocalDate.of(2025,1,1), LocalDate.of(2025,12,31), bank1, customer1);
        cardRepository.save(card1);
        bank1.addCustomer(customer1); customer1.addBank(bank1);
        customerRepository.save(customer1);bankRepository.save(bank1);

        Purchase purchase1 = new PurchaseSinglePayment("V001","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Purchase purchase2 = new PurchaseSinglePayment("V002","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Purchase purchase3 = new PurchaseSinglePayment("V003","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Purchase purchase4 = new PurchaseSinglePayment("V004","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        purchaseRepository.saveAll(List.of(purchase1,purchase2,purchase3,purchase4));
        purchase1.setCard(card1);purchase2.setCard(card1);purchase3.setCard(card1);purchase4.setCard(card1);
        card1.addPurchase(purchase1);card1.addPurchase(purchase2);card1.addPurchase(purchase3);card1.addPurchase(purchase4);
        purchaseRepository.saveAll(List.of(purchase1,purchase2,purchase3,purchase4));
        cardRepository.save(card1);

        Quota quota1 = new Quota(1,50.0f,"January","2024");
        Quota quota2 = new Quota(1,50.0f,"February","2024");
        quotaRepository.saveAll(List.of(quota1, quota2));
        PurchaseMonthlyPayments purchase5 = new PurchaseMonthlyPayments("V005","Store A","20-12345678-1",100.0f,100.0f,0.0f,2);
        purchaseRepository.save(purchase5);
        purchase5.addQuota(quota1);
        purchase5.addQuota(quota2);
        card1.addPurchase(purchase5);
        purchase5.setCard(card1);
        quotaRepository.saveAll(List.of(quota1, quota2));
        cardRepository.save(card1);
        purchaseRepository.save(purchase5);

        Quota quota3 = new Quota(1,50.0f,"January","2024");
        Quota quota4 = new Quota(1,50.0f,"February","2024");
        quotaRepository.save(quota3); quotaRepository.save(quota4);
        PurchaseMonthlyPayments purchase6 = new PurchaseMonthlyPayments("V006","Store A","20-12345678-1",100.0f,100.0f,0.0f,2);
        purchaseRepository.save(purchase6);
        purchase6.addQuota(quota3);
        purchase6.addQuota(quota4);
        card1.addPurchase(purchase6);purchase6.setCard(card1);
        purchaseRepository.save(purchase6);
        cardRepository.save(card1);

        // 4) Obtener el listado de tarjetas que vencen en los siguientes 30 dias
        Card card2 = new Card("2345678923456789","123","John Doe2",LocalDate.of(2025,1,1), LocalDate.now().minusDays(10), bank1, customer1);
        Card card3 = new Card("3456789234567891","346","John Doe3",LocalDate.of(2025,1,1), LocalDate.now().minusDays(20), bank1, customer1);
        Card card4 = new Card("4567892345678910","767","John Doe4",LocalDate.of(2025,1,1), LocalDate.now().minusDays(40), bank1, customer1);
        cardRepository.saveAll(List.of(card2,card3,card4));


        // 5) Obtener la informacion de una compra, incluyendo el listado de cuotas si esta posee
        Quota quota6 = new Quota(1,100.0f,"January","2024");
        Quota quota7 = new Quota(2,100.0f,"February","2024");
        Quota quota8 = new Quota(3,100.0f,"March","2024");
        quotaRepository.saveAll(List.of(quota6,quota7,quota8));
        PurchaseMonthlyPayments purchase7 = new PurchaseMonthlyPayments("V007","Store A","20-12345678-1",100.0f,100.0f,0.0f,3);
        purchaseRepository.save(purchase7);
        purchase7.addQuota(quota6);
        purchase7.addQuota(quota7);
        purchase7.addQuota(quota8);
        purchaseRepository.save(purchase7);
        Purchase purchase8 = new PurchaseSinglePayment("V005","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        purchaseRepository.save(purchase8);

        // 6) Eliminar una promocion a traves de su codigo
        // tener en cuenta que esta puede haber sido aplicada a alguna compra
        Promotion promo1 = new Discount("D002","Travel Discount","Store B","20-12345678-2", LocalDate.of(2024,1,1), LocalDate.of(2024,12,1),30.0f,100.0f,false);
        Purchase purchase9 = new PurchaseSinglePayment("V032","Store A","20-12345678-1",100.0f,70.0f,0.0f);
        promotionRepository.save(promo1); purchaseRepository.save(purchase9);
        promo1.addPurchase(purchase9);
        promotionRepository.save(promo1);

        // 7) Obtener el listado de las promociones disponibles de un local entre dos fechas
        Promotion promo2 = new Financing("F002","Back-to-School Financing","Store A","20-12345678-1", LocalDate.of(2024,2,1), LocalDate.of(2024,11,1),2,0.0f);
        Promotion promo3 = new Financing("F003","Back-to-School Financing","Store B","20-12345678-1", LocalDate.of(2024,2,1), LocalDate.of(2024,11,1),2,0.0f);
        Promotion promo4 = new Discount("D002","Travel Discount","Store B","20-12345678-2", LocalDate.of(2024,2,1), LocalDate.of(2024,11,1),30.0f,100.0f,false);
        Promotion promo5 = new Discount("D003","Travel Discount","Store B","20-12345678-2", LocalDate.of(2025,2,1), LocalDate.of(2025,11,1),30.0f,100.0f,false);
        promotionRepository.saveAll(List.of(promo2,promo3,promo4,promo5));

        // 8) Obtener la informacion de las 10 tarjetas con mas compras.
        Purchase purchase10 = new PurchaseSinglePayment("V006","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Purchase purchase11 = new PurchaseSinglePayment("V007","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Card card5 = new Card("1234583812345678","123","John Doe5",LocalDate.of(2025,1,1), LocalDate.of(2025,12,31), bank1, customer1);

        Purchase purchase12 = new PurchaseSinglePayment("V008","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Purchase purchase13 = new PurchaseSinglePayment("V009","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Card card6 = new Card("123451562345678","123","John Doe6",LocalDate.of(2025,1,1), LocalDate.of(2025,12,31), bank1, customer1);

        Purchase purchase14 = new PurchaseSinglePayment("V010","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Purchase purchase15 = new PurchaseSinglePayment("V011","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Card card7 = new Card("1234565412345678","123","John Doe7",LocalDate.of(2025,1,1), LocalDate.of(2025,12,31), bank1, customer1);

        Purchase purchase16 = new PurchaseSinglePayment("V012","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Purchase purchase17 = new PurchaseSinglePayment("V013","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Card card8 = new Card("123456842345678","123","John Doe8",LocalDate.of(2025,1,1), LocalDate.of(2025,12,31), bank1, customer1);

        Purchase purchase18 = new PurchaseSinglePayment("V014","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Purchase purchase19 = new PurchaseSinglePayment("V015","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Card card9 = new Card("1234567816655678","123","John Doe9",LocalDate.of(2025,1,1), LocalDate.of(2025,12,31), bank1, customer1);

        Purchase purchase20 = new PurchaseSinglePayment("V016","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Purchase purchase21 = new PurchaseSinglePayment("V017","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Card card10 = new Card("1234565932345678","123","John Doe10",LocalDate.of(2025,1,1), LocalDate.of(2025,12,31), bank1, customer1);

        Purchase purchase22 = new PurchaseSinglePayment("V018","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Purchase purchase23 = new PurchaseSinglePayment("V019","Store A","20-12345678-1",100.0f,100.0f,0.0f);
        Card card11 = new Card("1234567684345678","123","John Doe11",LocalDate.of(2025,1,1), LocalDate.of(2025,12,31), bank1, customer1);

        Purchase purchase24 = new PurchaseSinglePayment("V020","Store A","20-12345678-1",100.0f,90.0f,10.0f);
        Purchase purchase25 = new PurchaseSinglePayment("V021","Store A","20-12345678-1",100.0f,90.0f,10.0f);
        Card card12 = new Card("1234567294345678","123","John Doe12",LocalDate.of(2025,1,1), LocalDate.of(2025,12,31), bank1, customer1);

        Purchase purchase26 = new PurchaseSinglePayment("V022","Store A","20-12345678-1",100.0f,90.0f,10.0f);
        Purchase purchase27 = new PurchaseSinglePayment("V023","Store A","20-12345678-1",100.0f,90.0f,10.0f);
        Card card13 = new Card("1234567466345678","123","John Doe13",LocalDate.of(2025,1,1), LocalDate.of(2025,12,31), bank1, customer1);

        Purchase purchase28 = new PurchaseSinglePayment("V024","Store A","20-12345678-1",100.0f,90.0f,10.0f);
        Card card14 = new Card("1234522212345678","123","John Doe14",LocalDate.of(2025,1,1), LocalDate.of(2025,12,31), bank1, customer1);

        purchaseRepository.saveAll(List.of(purchase10, purchase11, purchase12, purchase13, purchase14, purchase15,
                purchase16, purchase17, purchase18, purchase19, purchase20, purchase21, purchase22, purchase23,
                purchase24, purchase25, purchase26, purchase27, purchase28));
        cardRepository.saveAll(List.of(card5, card6, card7, card8, card9, card10, card11,
                card12, card13, card14));

        purchase10.setCard(card5);purchase11.setCard(card5);
        card5.addPurchase(purchase10);card5.addPurchase(purchase11);
        purchase12.setCard(card6);purchase13.setCard(card6);
        card6.addPurchase(purchase12);card6.addPurchase(purchase13);
        purchase14.setCard(card7);purchase15.setCard(card7);
        card7.addPurchase(purchase14);card7.addPurchase(purchase15);
        purchase16.setCard(card8);purchase17.setCard(card8);
        card8.addPurchase(purchase16);card8.addPurchase(purchase17);
        purchase18.setCard(card9);purchase19.setCard(card9);
        card9.addPurchase(purchase18);card9.addPurchase(purchase19);
        purchase20.setCard(card10);purchase21.setCard(card10);
        card10.addPurchase(purchase20);card10.addPurchase(purchase21);
        purchase22.setCard(card11);purchase23.setCard(card11);
        card11.addPurchase(purchase22);card11.addPurchase(purchase23);
        purchase24.setCard(card12);purchase25.setCard(card12);
        card12.addPurchase(purchase24);card12.addPurchase(purchase25);
        purchase26.setCard(card13);purchase27.setCard(card13);
        card13.addPurchase(purchase26);card13.addPurchase(purchase27);
        purchase28.setCard(card14);card14.addPurchase(purchase28);

        purchaseRepository.saveAll(List.of(purchase10, purchase11, purchase12, purchase13, purchase14, purchase15,
                purchase16, purchase17, purchase18, purchase19, purchase20, purchase21, purchase22, purchase23,
                purchase24, purchase25, purchase26, purchase27, purchase28));
        cardRepository.saveAll(List.of(card5, card6, card7, card8, card9, card10, card11,
                card12, card13, card14));


        // 9) Obtener la promocion mas utilizada en las compras registradas
        Promotion promo6 = new Discount("D004","Travel Discount","Store A","20-12345678-1", LocalDate.of(2025,1,1), LocalDate.of(2025,12,31),10.0f,100.0f,false);
        promotionRepository.save(promo6);
        // Assign to 3 purchases
        purchase24.setValidPromotion(promo6);promo6.addPurchase(purchase24);
        purchase25.setValidPromotion(promo6);promo6.addPurchase(purchase25);
        purchase26.setValidPromotion(promo6);promo6.addPurchase(purchase26);
        purchase27.setValidPromotion(promo6);promo6.addPurchase(purchase27);
        purchase28.setValidPromotion(promo6);promo6.addPurchase(purchase28);
        purchaseRepository.saveAll(List.of(purchase24,purchase25,purchase26, purchase27));
        promotionRepository.save(promo6);


        // 10) Obtener el nombre y el CUIT del local que mas facturo en cierto mes
        Purchase purchase29 = new PurchaseSinglePayment("V025","Store B","20-12345678-2",100.0f,100.0f,0.0f);
        Purchase purchase30 = new PurchaseSinglePayment("V026","Store B","20-12345678-2",200.0f,200.0f,0.0f);
        Purchase purchase31 = new PurchaseSinglePayment("V027","Store C","20-12345678-3",100.0f,100.0f,0.0f);
        Purchase purchase32 = new PurchaseSinglePayment("V028","Store C","20-12345678-3",200.0f,200.0f,0.0f);
        Purchase purchase33 = new PurchaseSinglePayment("V029","Store C","20-12345678-3",300.0f,300.0f,0.0f);
        Purchase purchase34 = new PurchaseSinglePayment("V030","Store D","20-12345678-4",400.0f,400.0f,0.0f);
        Purchase purchase35 = new PurchaseSinglePayment("V031","Store D","20-12345678-4",500.0f,500.0f,0.0f);
        purchaseRepository.saveAll(List.of(purchase29, purchase30, purchase31,
                purchase32, purchase33, purchase34, purchase35));


        // 11) Obtener un listado con el numero de clientes de cada banco
        Customer customer2 = new Customer("Carolina Chavez","96385447","1213456789","Italia 860","2494251086",LocalDate.of(2024,1,1));
        Customer customer3 = new Customer("Carlos López","34567890","20345678901","Av. Independencia 789","2494251003",LocalDate.of(2024,1,1));
        Customer customer4 = new Customer("Laura Martínez","65432109","20654321098","Calle Rosales 101","2494251004",LocalDate.of(2024,1,1));
        Customer customer5 = new Customer("Miguel Torres","34567891","20345678912","Calle Tucumán 505","2494251009",LocalDate.of(2024,1,1));
        Customer customer6 = new Customer("Rubén Aguirre","45678903","20456789034","Av. Mitre 1515","2494251019",LocalDate.of(2024,1,1));
        Bank bank2 = new Bank("Banco Supervielle","30609876543","Calle Salta 789","1143325689");
        Bank bank3 = new Bank("Banco de Córdoba","30654321678","Calle 9 de Julio 234","1143325691");
        customerRepository.saveAll(List.of(customer2, customer3,customer4,customer5,customer6));
        bankRepository.save(bank2); bankRepository.save(bank3);
        bank2.addCustomer(customer2);
        customer2.addBank(bank2);
        bank2.addCustomer(customer3);customer3.addBank(bank2);
        bank3.addCustomer(customer4);customer4.addBank(bank3);
        bank3.addCustomer(customer5);customer5.addBank(bank3);
        bank3.addCustomer(customer6);customer6.addBank(bank3);
        customerRepository.saveAll(List.of(customer2, customer3,customer4,customer5,customer6));
        bankRepository.save(bank2); bankRepository.save(bank3);

    }
}