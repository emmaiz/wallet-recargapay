package com.recargapay.wallet.controller;

import com.recargapay.wallet.entity.User;
import com.recargapay.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestParam String name, @RequestParam String email) {
        User user = walletService.createUser(name, email);
        return ResponseEntity.ok(user);
    }
}