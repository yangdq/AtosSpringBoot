package com.inventsolv.springsecurityjpajwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.inventsolv.springsecurityjpajwt.filter.JwtRequestFilter;
import com.inventsolv.springsecurityjpajwt.services.MyUserDetailsService;

@EnableWebSecurity
@EnableTransactionManagement
public class SecurityConfigurer extends WebSecurityConfigurerAdapter{
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	//Authentication Configuration
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(myUserDetailsService);
	}
	

	/**
	 * Authorization logic, allowing "/authenticate" API calls to bypass security.
	 */
	@Override	
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable()
			.authorizeRequests()
		    .antMatchers("/authenticate").permitAll()
		    .antMatchers("/main").permitAll()
		    .antMatchers("/createuser").permitAll()
		    .antMatchers("/h2-console/**").permitAll()
		    .antMatchers("/api/**").permitAll()
		    .antMatchers("/swagger-ui.html").permitAll()
		    .antMatchers("/swagger-ui/**").permitAll()
		    .and()
		    .authorizeRequests()
		    .antMatchers("/admin").hasRole("ADMIN")
		    .antMatchers("/user").hasAnyRole("ADMIN", "USER")
		    .antMatchers("/finduser/**").hasAnyRole("ADMIN", "USER")
		    .antMatchers("/updateuser/**").hasAnyRole("ADMIN", "USER")
		    .antMatchers("/deleteuser/**").hasRole("ADMIN")
		    .antMatchers("/createaccount").hasAnyRole("ADMIN", "USER")
		    .antMatchers("/findaccount/**").hasAnyRole("ADMIN", "USER")
		    .antMatchers("/updateaccount/**").hasAnyRole("ADMIN", "USER")
		    .antMatchers("/deleteaccount/**").hasRole("ADMIN")	
		    .antMatchers("/findtransaction/**").hasAnyRole("ADMIN", "USER")
		    .antMatchers("/createtransaction").hasAnyRole("ADMIN", "USER")	    
			.anyRequest().permitAll()
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	    // making H2 console working
	    http.headers().frameOptions().disable();
			
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManager() throws Exception{
		return super.authenticationManager();
	}
	
	@Bean
	public PasswordEncoder passowrdEncoder() {
		//return NoOpPasswordEncoder.getInstance();
        DelegatingPasswordEncoder delPasswordEncoder=  (DelegatingPasswordEncoder)PasswordEncoderFactories.createDelegatingPasswordEncoder();
        BCryptPasswordEncoder bcryptPasswordEncoder =new BCryptPasswordEncoder(15);
        delPasswordEncoder.setDefaultPasswordEncoderForMatches(bcryptPasswordEncoder);
        return delPasswordEncoder; 
	}
	
	
}
