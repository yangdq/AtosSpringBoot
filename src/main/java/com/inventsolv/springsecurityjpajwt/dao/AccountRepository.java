package com.inventsolv.springsecurityjpajwt.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventsolv.springsecurityjpajwt.models.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByAccountNumber(String accountNumber);
}
