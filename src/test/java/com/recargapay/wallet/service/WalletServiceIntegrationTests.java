package com.recargapay.wallet.service;


import com.recargapay.wallet.controller.WalletController;
import com.recargapay.wallet.entity.User;
import com.recargapay.wallet.entity.Wallet;
import com.recargapay.wallet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WalletServiceIntegrationTests {

    @Autowired
    private WalletController walletController;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testWalletCreationAndBalance() {
        User user1 = new User();
        user1.setName("Test User 1");
        user1.setEmail("test1@example.com");
        user1 = userRepository.save(user1);

        ResponseEntity<Wallet> response = walletController.createWallet(user1.getId(), 150.0);
        Wallet wallet = response.getBody();

        assertNotNull(wallet);
        assertEquals(150.0, wallet.getBalance());

        ResponseEntity<Double> balanceResponse = walletController.getWalletBalance(wallet.getId());
        assertEquals(150.0, balanceResponse.getBody());
    }

    @Test
    public void testDepositIntegration() {

        User user1 = new User();
        user1.setName("Test User 1");
        user1.setEmail("test1@example.com");
        user1 = userRepository.save(user1);

        ResponseEntity<Wallet> walletResponse = walletController.createWallet(user1.getId(), 100.0);
        Wallet wallet = walletResponse.getBody();

        walletController.deposit(wallet.getId(), 50.0);

        ResponseEntity<Double> balanceResponse = walletController.getWalletBalance(wallet.getId());
        assertEquals(150.0, balanceResponse.getBody());
    }

    @Test
    public void testWithdrawIntegration() {
        User user1 = new User();
        user1.setName("Test User 1");
        user1.setEmail("test1@example.com");
        user1 = userRepository.save(user1);

        ResponseEntity<Wallet> walletResponse = walletController.createWallet(user1.getId(), 200.0);
        Wallet wallet = walletResponse.getBody();

        walletController.withdraw(wallet.getId(), 100.0);

        ResponseEntity<Double> balanceResponse = walletController.getWalletBalance(wallet.getId());
        assertEquals(100.0, balanceResponse.getBody());
    }

    @Test
    public void testTransferIntegration() {

        User user1 = new User();
        user1.setName("Test User 1");
        user1.setEmail("test1@example.com");
        user1 = userRepository.save(user1);

        User user2 = new User();
        user2.setName("Test User 2");
        user2.setEmail("test2@example.com");
        user2 = userRepository.save(user2);


        ResponseEntity<Wallet> sourceWalletResponse = walletController.createWallet(user1.getId(), 300.0);
        Wallet sourceWallet = sourceWalletResponse.getBody();

        ResponseEntity<Wallet> targetWalletResponse = walletController.createWallet(user2.getId(), 100.0);
        Wallet targetWallet = targetWalletResponse.getBody();

        walletController.transfer(sourceWallet.getId(), targetWallet.getId(), 50.0);

        ResponseEntity<Double> sourceBalanceResponse = walletController.getWalletBalance(sourceWallet.getId());
        ResponseEntity<Double> targetBalanceResponse = walletController.getWalletBalance(targetWallet.getId());

        assertEquals(250.0, sourceBalanceResponse.getBody());
        assertEquals(150.0, targetBalanceResponse.getBody());
    }
}
