package com.inventsolv.springsecurityjpajwt.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inventsolv.springsecurityjpajwt.dao.UserRepository;
import com.inventsolv.springsecurityjpajwt.models.User;
import com.inventsolv.springsecurityjpajwt.to.CustomerTO;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordEncoder passowrdEncoder;
    
    public CustomerTO createUser(CustomerTO customerTO) {
    	User user = covertFromTO(customerTO);
    	String password = customerTO.getPassword();
    	user.setPassword(passowrdEncoder.encode(password));
    	user.setCustomerNumber(UUID.randomUUID().toString());
    	user.setActive(true);
    	User savedUser = userRepository.save(user);	
    	return covertFromJPA(savedUser);
    }
    
    /** 
     * Soft delete customer Info.
     * @param userName
     */
    public void deleteUser(String userName) {
    	Optional<User> existingUser = userRepository.findByUserName(userName);
    	if(existingUser.isPresent()) {
    		User userFound = existingUser.get();
    		userFound.setActive(false);
    		userRepository.save(userFound);
    	}
    }
    
    /**
     * Find User based on User Name.
     * @param userName
     * @return
     */
    public CustomerTO findUser(String userName) {
    	Optional<User> existingUser = userRepository.findByUserName(userName);
    	CustomerTO to = new CustomerTO();
    	if(existingUser.isPresent()) {
    		return covertFromJPA(existingUser.get());
    	}
    	return to;
    }
    
    /**
     * Update User Info.
     * @param user
     * @return
     */
    public CustomerTO updateUser(CustomerTO customerTO) {
    	Optional<User> existingUserOpt = userRepository.findByUserName(customerTO.getUserName());
    	if(existingUserOpt.isPresent()) {
    		User existingUser = existingUserOpt.get();
    		existingUser.setFirstName(customerTO.getFirstName());
    		existingUser.setLastName(customerTO.getLastName());
    		existingUser.setRoles(customerTO.getRoles());
    		existingUser.setUserName(customerTO.getUserName());
    		return covertFromJPA(userRepository.save(existingUser));
    	}
    	return customerTO;
    }
    
    private User covertFromTO(CustomerTO customerTO) {
    	User user = new User();
    	user.setCustomerNumber(customerTO.getCustomerNumber());
    	user.setFirstName(customerTO.getFirstName());
    	user.setLastName(customerTO.getLastName());
    	user.setRoles(customerTO.getRoles());
    	user.setUserName(customerTO.getUserName());
    	return user;
    }
    
    private CustomerTO covertFromJPA(User user) {
    	CustomerTO to = new CustomerTO();
    	to.setCustomerNumber(user.getCustomerNumber());
    	to.setFirstName(user.getFirstName());
    	to.setLastName(user.getLastName());
    	to.setRoles(user.getRoles());
    	to.setUserName(user.getUserName());
    	return to;
    }
}
