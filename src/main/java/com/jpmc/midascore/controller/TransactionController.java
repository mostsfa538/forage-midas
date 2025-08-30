package com.jpmc.midascore.controller;

import com.jpmc.midascore.dto.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.services.TransactionsServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    private final TransactionsServices transactionsServices;

    public TransactionController(TransactionsServices transactionsServices) {
        this.transactionsServices = transactionsServices;
    }

    @PostMapping("/transaction")  // Changed from "/incentive"
    public float processTransaction(@RequestBody Transaction transaction) {
        return transactionsServices.processTransaction(transaction);
    }
}
