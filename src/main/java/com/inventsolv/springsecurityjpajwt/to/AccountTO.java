package com.inventsolv.springsecurityjpajwt.to;

import java.math.BigDecimal;

public class AccountTO {
	
    private String accountNumber;
    private BigDecimal balance;
    private String customerNumber;
    

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}


}
