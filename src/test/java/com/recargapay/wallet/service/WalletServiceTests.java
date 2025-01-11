package com.recargapay.wallet.service;

import static org.junit.jupiter.api.Assertions.*;

import com.recargapay.wallet.entity.User;
import com.recargapay.wallet.entity.Wallet;
import com.recargapay.wallet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class WalletServiceTests {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User user = walletService.createUser("testUser", "test@example.com");
        assertNotNull(user.getId());
        assertEquals("testUser", user.getName());
    }

    @Test
    public void testCreateWallet() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user = userRepository.save(user);

        Wallet wallet = walletService.createWallet(user.getId(), 100.0);
        assertNotNull(wallet);
        assertEquals(100.0, wallet.getBalance());
        assertEquals(user.getId(), wallet.getUser().getId());
    }

    @Test
    public void testDeposit() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user = userRepository.save(user);

        Wallet wallet = walletService.createWallet(user.getId(), 50.0);
        wallet = walletService.deposit(wallet.getId(), 25.0);

        assertEquals(75.0, wallet.getBalance());
        assertEquals(user.getId(), wallet.getUser().getId());
    }

    @Test
    public void testWithdraw() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user = userRepository.save(user);

        Wallet wallet = walletService.createWallet(user.getId(), 100.0);
        wallet = walletService.withdraw(wallet.getId(), 40.0);
        assertEquals(60.0, wallet.getBalance());
    }

    @Test
    public void testTransfer() {
        User user1 = new User();
        user1.setName("Test User 1");
        user1.setEmail("test1@example.com");
        user1 = userRepository.save(user1);

        User user2 = new User();
        user2.setName("Test User 2");
        user2.setEmail("test2@example.com");
        user2 = userRepository.save(user2);

        Wallet sourceWallet = walletService.createWallet(user1.getId(), 200.0);
        Wallet targetWallet = walletService.createWallet(user2.getId(), 50.0);
        walletService.transfer(sourceWallet.getId(), targetWallet.getId(), 50.0);

        assertEquals(150.0, walletService.getWalletBalance(sourceWallet.getId()));
        assertEquals(100.0, walletService.getWalletBalance(targetWallet.getId()));
    }
}