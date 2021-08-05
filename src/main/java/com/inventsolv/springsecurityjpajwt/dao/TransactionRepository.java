package com.inventsolv.springsecurityjpajwt.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventsolv.springsecurityjpajwt.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findByTransactionNumber(String transactionNumber);
}
