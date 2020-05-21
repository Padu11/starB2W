package br.com.paulomoreira.starB2W.resource.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Data;

@Data
public class LoginRequest {
	
	private String email;
	private String pass;
	

	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(email, pass);
	}

}
