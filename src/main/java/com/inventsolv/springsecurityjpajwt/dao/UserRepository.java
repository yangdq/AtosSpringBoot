package com.inventsolv.springsecurityjpajwt.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inventsolv.springsecurityjpajwt.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query(value = "SELECT * FROM CUSTOMER WHERE USER_NAME = ?1 AND ACTIVE = 1", nativeQuery = true)
    Optional<User> findByUserName(String userName);
    
	@Query(value = "SELECT * FROM CUSTOMER WHERE CUSTOMER_NUMBER = ?1 AND ACTIVE = 1", nativeQuery = true)
    Optional<User> findByCustomerNumber(String customerNumber);
}
