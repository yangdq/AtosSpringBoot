package com.inventsolv.springsecurityjpajwt;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTests {

	@Test
	public void testPasswordEnconding() {
		String encoded = new BCryptPasswordEncoder(15).encode("pass");
		System.out.println(encoded);
	}
}
