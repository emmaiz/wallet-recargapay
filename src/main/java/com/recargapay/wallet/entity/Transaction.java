package com.recargapay.wallet.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    private Double amount;
    private LocalDateTime timestamp;
    private String type; // "DEPOSIT", "WITHDRAWAL", "TRANSFER"

    @ManyToOne
    @JoinColumn(name = "target_wallet_id")
    private Wallet targetWallet; // For transfer transactions
}