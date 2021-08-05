package com.inventsolv.springsecurityjpajwt.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventsolv.springsecurityjpajwt.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
    
    Optional<User> findByCustomerNumber(String customerNumber);
}
