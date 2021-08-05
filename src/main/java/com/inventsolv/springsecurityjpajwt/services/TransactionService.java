package com.inventsolv.springsecurityjpajwt.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventsolv.banking.errors.EntityNotFoundException;
import com.inventsolv.springsecurityjpajwt.dao.AccountRepository;
import com.inventsolv.springsecurityjpajwt.dao.TransactionRepository;
import com.inventsolv.springsecurityjpajwt.models.Account;
import com.inventsolv.springsecurityjpajwt.models.Transaction;
import com.inventsolv.springsecurityjpajwt.to.TransactionTO;

@Service
public class TransactionService {
	private static final String CREDIT = "CREDIT";
	private static final String DEBIT = "DEBIT";

    @Autowired
    TransactionRepository transactionRepository;
    
    @Autowired
    AccountRepository accountRepository;
       
    @Transactional
    /**
     * Create transaction and update account balances.
     * @param transactionTO
     * @return
     */
    public TransactionTO createTransaction(TransactionTO transactionTO) {
    	String toAccountNumber = transactionTO.getToAccountNumber();
    	String fromAccountNumber = transactionTO.getFromAccountNumber();
    	Optional<Account> toAccount = accountRepository.findByAccountNumber(toAccountNumber);
    	
    	Transaction transaction = handleAccountBalance(transactionTO);
    	transaction.setTransactionNumber(UUID.randomUUID().toString());
    	transaction.setActive(true);
    	
    	Transaction savedTransaction = transactionRepository.save(transaction);
    	transactionTO.setTransactionNumber(savedTransaction.getTransactionNumber());
    	
    	
    	
    	return transactionTO;
    }
    
    private Transaction handleAccountBalance(TransactionTO transaction) {
    	String toAccountNumber = transaction.getToAccountNumber();
    	String fromAccountNumber = transaction.getFromAccountNumber();
    	Optional<Account> toAccount = accountRepository.findByAccountNumber(toAccountNumber);
    	Transaction tranToPersist = new Transaction();
    	if(toAccountNumber == null) {
    		throw new EntityNotFoundException("Transaction need toAccount number");
    	}   
    	if(! toAccount.isPresent()) {
    		throw new EntityNotFoundException(toAccountNumber + " is not a valid account number!");
    	}else {
    		tranToPersist.setToAccountId(toAccount.get().getId());
    		tranToPersist.setAmount(transaction.getAmount());
    		tranToPersist.setTransactionType(transaction.getTransactionType());
    		tranToPersist.setTransactionTime(new Date());
    	}
    	if(fromAccountNumber != null) {
    		Optional<Account> fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
    		if(! fromAccount.isPresent()) {
    			throw new EntityNotFoundException(fromAccountNumber + " is not a valid account number!");
    		}
    		tranToPersist.setFromAccountId(fromAccount.get().getId());
    		
    		updateAccountBalance(toAccount.get(), fromAccount.get(), transaction.getAmount(), transaction.getTransactionType());
    	}else {
    		updateAccountBalance(toAccount.get(), null, transaction.getAmount(), transaction.getTransactionType());
    	}
    	
    	return tranToPersist;
    }
    
    private void updateAccountBalance(Account toAccount, Account fromAccount, BigDecimal amount, String transactionType) {
    	if(CREDIT.equalsIgnoreCase(transactionType)) {
    		toAccount.setBalance(toAccount.getBalance().add(amount));
    		if(fromAccount != null) {
    			fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
    		}
    	}
    	else if(DEBIT.equalsIgnoreCase(transactionType)) {
    		toAccount.setBalance(toAccount.getBalance().subtract(amount));
    		if(fromAccount != null) {
    			fromAccount.setBalance(fromAccount.getBalance().add(amount));
    		}
    	}
    	accountRepository.save(toAccount);
    	if(fromAccount != null) {
    		accountRepository.save(fromAccount);
    	}
    	
    }
//    /**
//     * Soft delete transactions.
//     * @param transaction
//     */
//    @Transactional
//    public void deleteTransaction(Transaction transaction) {
//    	Optional<Transaction> existingTransaction = transactionRepository.findByTransactionNumber(transaction.getTransactionNumber());
//    	if(existingTransaction.isPresent()) {
//    		transaction = existingTransaction.get();
//    		transaction.setActive(false);
//    		transactionRepository.save(transaction);
//    	}else {
//    		throw new EntityNotFoundException("Transaction not deleted for transaction number " + transaction.getTransactionNumber());
//    	}
//    }
    
    public TransactionTO findTransaction(String transactionNumber) {
    	Optional<Transaction> existingTransaction = transactionRepository.findByTransactionNumber(transactionNumber);
    	TransactionTO transactionTO = new TransactionTO();
    	if(! existingTransaction.isPresent()) {
    		return transactionTO;
    	}
    	Transaction tran = existingTransaction.get();
    	transactionTO.setTransactionNumber(transactionNumber);
    	transactionTO.setAmount(tran.getAmount());
    	transactionTO.setTransactionType(tran.getTransactionType());
    	transactionTO.setTransactionTime(tran.getTransactionTime());
    	transactionTO.setToAccountNumber(accountRepository.findById(tran.getToAccountId()).get().getAccountNumber());
    	if(tran.getFromAccountId() != 0) {
        	transactionTO.setFromAccountNumber(accountRepository.findById(tran.getFromAccountId()).get().getAccountNumber());
    	}	
    	return transactionTO;    	
    }
}


