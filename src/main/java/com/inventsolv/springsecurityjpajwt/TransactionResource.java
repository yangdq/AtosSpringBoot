package com.inventsolv.springsecurityjpajwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inventsolv.springsecurityjpajwt.services.TransactionService;
import com.inventsolv.springsecurityjpajwt.to.TransactionTO;

@RestController 
public class TransactionResource {
		
	@Autowired
	private TransactionService transactionService;
    
    @PostMapping(value="/createtransaction")
	public ResponseEntity<TransactionTO> createTransaction(@RequestBody TransactionTO transaction) throws Exception{
    	TransactionTO savedTransactionTO = transactionService.createTransaction(transaction);
    	return ResponseEntity.ok(savedTransactionTO);
	}
    
    @GetMapping(value="/findtransaction/{transactionNumber}")
	public ResponseEntity<TransactionTO> getTransaction(@PathVariable String transactionNumber) throws Exception{
    	TransactionTO transaction = transactionService.findTransaction(transactionNumber);
    	return ResponseEntity.ok(transaction);
	}

}
