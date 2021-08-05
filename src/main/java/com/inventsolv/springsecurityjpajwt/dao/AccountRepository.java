package com.inventsolv.springsecurityjpajwt.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inventsolv.springsecurityjpajwt.models.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	@Query(value = "SELECT * FROM ACCOUNT WHERE ACCOUNT_NUMBER = ?1 AND ACTIVE = 1", nativeQuery = true)
    Optional<Account> findByAccountNumber(String accountNumber);
}
