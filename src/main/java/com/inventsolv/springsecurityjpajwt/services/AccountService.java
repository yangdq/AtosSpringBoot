package com.inventsolv.springsecurityjpajwt.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventsolv.banking.errors.EntityNotFoundException;
import com.inventsolv.springsecurityjpajwt.dao.AccountRepository;
import com.inventsolv.springsecurityjpajwt.dao.UserRepository;
import com.inventsolv.springsecurityjpajwt.models.Account;
import com.inventsolv.springsecurityjpajwt.models.User;
import com.inventsolv.springsecurityjpajwt.to.AccountTO;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    UserRepository userRepository;
        
    public AccountTO createAccount(AccountTO accountTO) {
    	String customerNumber = accountTO.getCustomerNumber();
    	Optional<User> existingUser = userRepository.findByCustomerNumber(customerNumber);
    	if(existingUser.isPresent()) {
    		Account account = covertFromTO(accountTO);
    		account.setUser(existingUser.get());
    		account.setAccountNumber(UUID.randomUUID().toString());
    		account.setActive(true);
    		Account savedAccount = accountRepository.save(account);
    		return covertFromJPA(savedAccount);
    	}else {
    		throw new EntityNotFoundException("Account not created for customer with customer number " + customerNumber);
    	}
    }
    
    /**
     * Soft delete account.
     * @param accountNumber
     */
    public void deleteAccount(String accountNumber) {
    	Optional<Account> existingAccountOpt = accountRepository.findByAccountNumber(accountNumber);
    	if(existingAccountOpt.isPresent()) {
    		Account existingAccount = existingAccountOpt.get();
    		existingAccount.setActive(false);
    		accountRepository.save(existingAccount);
    	}else {
    		throw new EntityNotFoundException("Account not deleted for account number " + accountNumber);
    	}
    }
    
    public AccountTO findAccount(String accountNumber) {
    	Optional<Account> existingAccount = accountRepository.findByAccountNumber(accountNumber);
    	AccountTO to;
    	if(existingAccount.isPresent())	{
    		to = covertFromJPA(existingAccount.get());
    	}else {
    		to = new AccountTO();
    	}
    	return to;
    }
    
    public AccountTO updateAccount(AccountTO account) {
    	Optional<Account> existingAccountOpt = accountRepository.findByAccountNumber(account.getAccountNumber());
    	if(existingAccountOpt.isPresent()) {
    		Account existingAccount = existingAccountOpt.get();
    		if(account.getBalance() != null) {
    			existingAccount.setBalance(account.getBalance());
    		}
    		return covertFromJPA(accountRepository.save(existingAccount));
    	}
    	return account;
    }
    
    private Account covertFromTO(AccountTO accountTO) {
    	Account account = new Account();
    	account.setBalance(accountTO.getBalance());
    	account.setAccountNumber(accountTO.getAccountNumber());
    	return account;
    }
    
    private AccountTO covertFromJPA(Account account) {
    	AccountTO accountTO = new AccountTO();
    	accountTO.setBalance(account.getBalance());
    	accountTO.setAccountNumber(account.getAccountNumber());
    	accountTO.setCustomerNumber(account.getUser().getCustomerNumber());
    	return accountTO;
    }
}
