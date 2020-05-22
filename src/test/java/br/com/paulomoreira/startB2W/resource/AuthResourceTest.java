package br.com.paulomoreira.startB2W.resource;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.paulomoreira.starB2W.StarB2WApplication;
import br.com.paulomoreira.starB2W.exception.AttributeException;
import br.com.paulomoreira.starB2W.model.Planet;
import br.com.paulomoreira.starB2W.resource.auth.AuthResource;
import br.com.paulomoreira.starB2W.resource.auth.LoginRequest;
import br.com.paulomoreira.starB2W.resource.auth.service.TokenService;
import br.com.paulomoreira.starB2W.resource.planet.dto.PlanetRequest;
import br.com.paulomoreira.starB2W.util.Converter;
import io.restassured.http.ContentType;

@SpringBootTest(classes = StarB2WApplication.class)
@ActiveProfiles("test")
public class AuthResourceTest {

	@Autowired
	AuthResource authResource;

	@MockBean
	private AuthenticationManager authManager;

	@MockBean
	private TokenService tokenService;

	@MockBean
	Converter converter;

	@BeforeEach
	public void setup() {
		standaloneSetup(this.authResource);

	}

	@Test
	public void returnSuccess_whenAuth_isValid() throws JsonProcessingException, AttributeException {

		LoginRequest login = new LoginRequest();
		login.setEmail("email");
		login.setPass("pass");

		when(converter.login(login.getEmail(), login.getPass())).thenReturn(null);
		when(authManager.authenticate(null)).thenReturn(null);
		when(tokenService.generateToken(null)).thenReturn("teste");

		authResource.auth(login);

		given().accept(ContentType.JSON).contentType(ContentType.JSON).body(login).when().post("/auth").then()
				.statusCode(HttpStatus.OK.value());

	}

}