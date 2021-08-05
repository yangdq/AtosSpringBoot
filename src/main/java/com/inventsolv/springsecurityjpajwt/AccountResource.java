package com.inventsolv.springsecurityjpajwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inventsolv.springsecurityjpajwt.services.AccountService;
import com.inventsolv.springsecurityjpajwt.services.TransactionService;
import com.inventsolv.springsecurityjpajwt.services.UserService;
import com.inventsolv.springsecurityjpajwt.to.AccountTO;
import com.inventsolv.springsecurityjpajwt.util.JwtUtil;

@RestController 
public class AccountResource {
		
	@Autowired
	private AccountService accountService;
    
    @PostMapping(value="/createaccount")
	public ResponseEntity<AccountTO> createAccountTO(@RequestBody AccountTO account) throws Exception{
    	AccountTO savedAccountTO = accountService.createAccount(account);
    	return ResponseEntity.ok(savedAccountTO);
	}
    
    @GetMapping(value="/findaccount/{accountNumber}")
	public ResponseEntity<AccountTO> getAccountTO(@PathVariable String accountNumber) throws Exception{
    	AccountTO account = accountService.findAccount(accountNumber);
    	return ResponseEntity.ok(account);
	}
    
    @DeleteMapping(value="/deleteaccount/{accountNumber}")
	public void deleteAccountTO(@PathVariable String accountNumber) throws Exception{
    	accountService.deleteAccount(accountNumber);
	}
    
    @PutMapping(value="/updateaccount")
	public ResponseEntity<AccountTO> updateAccountTO(@RequestBody AccountTO account) throws Exception{
    	AccountTO updatedAccountTO = accountService.updateAccount(account);
    	return ResponseEntity.ok(updatedAccountTO);
	}

}
