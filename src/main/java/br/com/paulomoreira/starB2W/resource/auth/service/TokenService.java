package br.com.paulomoreira.starB2W.resource.auth.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.paulomoreira.starB2W.model.User;
import br.com.paulomoreira.starB2W.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${starB2W.jwt.expiration}")
	private String expiration;

	@Value("${starB2W.jwt.secret}")
	private String secret;

	public String generateToken(Authentication auth) {

		User logged = (User) auth.getPrincipal();
		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

		return Jwts.builder().setIssuer(Constants.API_STARB2W).setSubject(logged.getId().toString()).setIssuedAt(today)
				.setExpiration(expirationDate).signWith(SignatureAlgorithm.HS256, secret).compact();

	}

	public String getUserId(String token) {

		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();

		return claims.getSubject();

	}

	public Boolean tokenValidation(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
