package com.jpmc.midascore.services;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionListener {

    private final TransactionsServices transactionsServices;

    public TransactionListener(TransactionsServices transactionsServices) {
        this.transactionsServices = transactionsServices;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-consumer")
    public void listen(Transaction transaction) {
        try {
            transactionsServices.processTransaction(transaction);
        } catch (RuntimeException e) {
            System.err.println("Failed to process transaction: " + e.getMessage());
        }
    }
}
