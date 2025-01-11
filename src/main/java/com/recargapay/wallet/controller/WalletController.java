package com.recargapay.wallet.controller;


import com.recargapay.wallet.entity.Wallet;
import com.recargapay.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/create")
    public ResponseEntity<Wallet> createWallet(@RequestParam Long userId, @RequestParam Double initialBalance) {
        Wallet wallet = walletService.createWallet(userId, initialBalance);
        return ResponseEntity.ok(wallet);
    }

    @GetMapping("/{walletId}/balance")
    public ResponseEntity<Double> getWalletBalance(@PathVariable Long walletId) {
        Double balance = walletService.getWalletBalance(walletId);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<Wallet> deposit(@PathVariable Long walletId, @RequestParam Double amount) {
        Wallet wallet = walletService.deposit(walletId, amount);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/{walletId}/withdraw")
    public ResponseEntity<Wallet> withdraw(@PathVariable Long walletId, @RequestParam Double amount) {
        Wallet wallet = walletService.withdraw(walletId, amount);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/{sourceWalletId}/transfer/{targetWalletId}")
    public ResponseEntity<String> transfer(@PathVariable Long sourceWalletId, @PathVariable Long targetWalletId, @RequestParam Double amount) {
        walletService.transfer(sourceWalletId, targetWalletId, amount);
        return ResponseEntity.ok("Transfer successful");
    }
}