package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    private final TransactionHandler transactionHandler;

    public TransactionListener(TransactionHandler transactionsServices) {
        this.transactionHandler = transactionsServices;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-consumer")
    public void listen(Transaction transaction) {
        try {
            transactionHandler.handleTransaction(transaction);
        } catch (RuntimeException e) {
            System.err.println("Failed to process transaction: " + e.getMessage());
        }
    }
}
