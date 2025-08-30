package com.jpmc.midascore.services;

import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.dto.Incentive;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class TransactionsServices {
    private final UserRepository userRepository;
    private final DatabaseConduit databaseConduit;
    private final RestTemplate restTemplate;

    public TransactionsServices(UserRepository userRepository, DatabaseConduit databaseConduit, RestTemplate restTemplate) {
        this.databaseConduit = databaseConduit;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public float processTransaction(Transaction transaction) {
        Long sendId = transaction.getSenderId();
        Long recipientId = transaction.getRecipientId();

        Optional<UserRecord> senderRecord =  userRepository.findById(sendId);
        Optional<UserRecord> recipientRecord =  userRepository.findById(recipientId);

        if (senderRecord.isEmpty()) {
            throw new RuntimeException("Sender not found");
        } if (recipientRecord.isEmpty()) {
            throw new RuntimeException("Recipient not found");
        }

        UserRecord sender = senderRecord.get();
        UserRecord recipient = recipientRecord.get();

        if (sender.getBalance() < transaction.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        Incentive incentiveResponse = restTemplate.postForObject(
                "http://localhost:8080/incentive",
                transaction,
                Incentive.class
        );

        float incentiveAmount = incentiveResponse != null ? incentiveResponse.getAmount() : 0;
        sender.setBalance(sender.getBalance() - transaction.getAmount());
        recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);

        databaseConduit.save(sender);
        databaseConduit.save(recipient);

        TransactionRecord transactionRecord = new TransactionRecord(
                transaction.getSenderId(),
                transaction.getRecipientId(),
                transaction.getAmount(),
                incentiveAmount
        );

        databaseConduit.saveTransaction(transactionRecord);
        return recipient.getBalance();
    }
}
