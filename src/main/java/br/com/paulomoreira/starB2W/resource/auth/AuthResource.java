package br.com.paulomoreira.starB2W.resource.auth;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.paulomoreira.starB2W.resource.auth.service.TokenService;
import br.com.paulomoreira.starB2W.util.Constants;
import br.com.paulomoreira.starB2W.util.Converter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(path = "/auth")
@Api(value = "/auth", tags = "Operations about authentication.")
public class AuthResource {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private TokenService tokenService;

	@Autowired
	Converter converter;

	@PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Authentication.")
	public ResponseEntity<?> auth(@RequestBody @Valid LoginRequest login) {

		UsernamePasswordAuthenticationToken loginData = converter.login(login.getEmail(), login.getPass());

		try {
			Authentication auth = authManager.authenticate(loginData);

			String token = tokenService.generateToken(auth);

			HashMap<String, String> tokenMap = new HashMap<>();

			tokenMap.put("token", token);
			tokenMap.put("authType", "Bearer");

			return ResponseEntity.ok(tokenMap);

		} catch (AuthenticationException e) {

			HashMap<String, String> message = new HashMap<String, String>();
			
			message.put(Constants.MESSAGE, Constants.USER_PASSWORD_INVALID);
			
			
			return ResponseEntity.badRequest().body(message);
		}

	}

}
