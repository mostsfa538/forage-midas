package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Balance;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {
    private final DatabaseConduit databaseConduit;

    public TransactionController(DatabaseConduit databaseConduit) {
        this.databaseConduit = databaseConduit;
    }

    @GetMapping(value = "/balance")
    public Balance queryBalance(@RequestParam("userId") Long userId) {
        float balance = databaseConduit.queryUserBalance(userId);
        return new Balance(balance);
    }
}
