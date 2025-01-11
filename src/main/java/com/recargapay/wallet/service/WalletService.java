package com.recargapay.wallet.service;


import com.recargapay.wallet.entity.Transaction;
import com.recargapay.wallet.entity.User;
import com.recargapay.wallet.entity.Wallet;
import com.recargapay.wallet.repository.TransactionRepository;
import com.recargapay.wallet.repository.UserRepository;
import com.recargapay.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;


    public User createUser(String name, String email) {

        /* TODO:si llego controlo que el email sea unico

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("User already exists with this email");
        }*/

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return userRepository.save(user);
    }

    public Wallet createWallet(Long userId, Double initialBalance) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(initialBalance);
        return walletRepository.save(wallet);
    }

    public Double getWalletBalance(Long walletId) {
        return walletRepository.findById(walletId).map(Wallet::getBalance).orElse(0.0);
    }

    public Wallet deposit(Long walletId, Double amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new RuntimeException("Wallet not found"));
        wallet.setBalance(wallet.getBalance() + amount);

        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount);
        transaction.setType("DEPOSIT");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        return walletRepository.save(wallet);
    }

    public Wallet withdraw(Long walletId, Double amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new RuntimeException("Wallet not found"));
        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        wallet.setBalance(wallet.getBalance() - amount);

        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(-amount);
        transaction.setType("WITHDRAWAL");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        return walletRepository.save(wallet);
    }

    public void transfer(Long sourceWalletId, Long targetWalletId, Double amount) {
        Wallet sourceWallet = walletRepository.findById(sourceWalletId).orElseThrow(() -> new RuntimeException("Source wallet not found"));
        Wallet targetWallet = walletRepository.findById(targetWalletId).orElseThrow(() -> new RuntimeException("Target wallet not found"));

        if (sourceWallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance in source wallet");
        }

        sourceWallet.setBalance(sourceWallet.getBalance() - amount);
        targetWallet.setBalance(targetWallet.getBalance() + amount);

        Transaction transaction = new Transaction();
        transaction.setWallet(sourceWallet);
        transaction.setTargetWallet(targetWallet);
        transaction.setAmount(-amount);
        transaction.setType("TRANSFER");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        Transaction targetTransaction = new Transaction();
        targetTransaction.setWallet(targetWallet);
        targetTransaction.setTargetWallet(sourceWallet);
        targetTransaction.setAmount(amount);
        targetTransaction.setType("TRANSFER");
        targetTransaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(targetTransaction);

        walletRepository.save(sourceWallet);
        walletRepository.save(targetWallet);
    }
}
