package com.jpmc.midascore.controller;

import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {
    private final UserServices userServices;

    public TransactionController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping("/balance")
    public ResponseEntity<Balance> getBalance(@RequestParam Long userId) {
        return ResponseEntity.ok().body(userServices.getBalance(userId));
    }
}
