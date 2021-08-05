package com.inventsolv.springsecurityjpajwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inventsolv.springsecurityjpajwt.models.AuthenticationRequest;
import com.inventsolv.springsecurityjpajwt.models.AuthenticationResponse;
import com.inventsolv.springsecurityjpajwt.services.AccountService;
import com.inventsolv.springsecurityjpajwt.services.MyUserDetailsService;
import com.inventsolv.springsecurityjpajwt.services.TransactionService;
import com.inventsolv.springsecurityjpajwt.services.UserService;
import com.inventsolv.springsecurityjpajwt.to.CustomerTO;
import com.inventsolv.springsecurityjpajwt.util.JwtUtil;

@RestController 
public class BankingResource {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@RequestMapping("/main")
	public String hello() {
		return "Welcome to the banking page.  Please create a customer first and then create account for further transactions";
	}
	
    @GetMapping("/user")
    public String user() {
        return ("<h1>User Page</h1>");
    }

    @GetMapping("/admin")
    public String admin() {
        return ("<h1>Admin Page</h1>");
    }
    
    @PostMapping(value="/createuser")
	public ResponseEntity<CustomerTO> createUser(@RequestBody CustomerTO user) throws Exception{
    	CustomerTO savedUser = userService.createUser(user);
    	return ResponseEntity.ok(savedUser);
	}
    
    @GetMapping(value="/finduser/{userName}")
	public ResponseEntity<CustomerTO> getUser(@PathVariable String userName) throws Exception{
    	CustomerTO user = userService.findUser(userName);
    	return ResponseEntity.ok(user);
	}
    
    @DeleteMapping(value="/deleteuser/{userName}")
	public void deleteUser(@PathVariable String userName) throws Exception{
    	userService.deleteUser(userName);
	}
    
    @PutMapping(value="/updateuser")
	public ResponseEntity<CustomerTO> updateUser(@RequestBody CustomerTO user) throws Exception{
    	CustomerTO updatedUser = userService.updateUser(user);
    	return ResponseEntity.ok(updatedUser);
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
														authenticationRequest.getPassword()));
		}catch(BadCredentialsException e) {
			throw new Exception("Incorrect username or password");
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	
	
	}
}
