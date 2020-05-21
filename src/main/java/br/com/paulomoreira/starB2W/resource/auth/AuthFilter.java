package br.com.paulomoreira.starB2W.resource.auth;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.paulomoreira.starB2W.model.User;
import br.com.paulomoreira.starB2W.resource.auth.service.TokenService;
import br.com.paulomoreira.starB2W.util.Constants;

public class AuthFilter extends OncePerRequestFilter {

	private TokenService tokenService;

	private UserRepository userRepository;

	public AuthFilter(UserRepository userRepositor, TokenService tokenService) {
		super();
		this.userRepository = userRepositor;
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = recoveryToken(request);

		Boolean isValid = tokenService.tokenValidation(token);
		if (isValid == true) {

			this.authClient(token);
		}

		filterChain.doFilter(request, response);

	}

	private void authClient(String token) {

		String userId = tokenService.getUserId(token);
		Optional<User> user = userRepository.findById(userId);

		Authentication auth = new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(auth);

	}

	private String recoveryToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty() || !token.startsWith(Constants.BEARER)) {
			return null;

		}
		return token.substring(7, token.length());

	}

}
