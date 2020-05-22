package br.com.paulomoreira.startB2W.resource;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

import java.beans.Encoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
import br.com.paulomoreira.starB2W.resource.auth.service.TokenService;
import br.com.paulomoreira.starB2W.resource.planet.PlanetResource;
import br.com.paulomoreira.starB2W.resource.planet.PlanetService;
import br.com.paulomoreira.starB2W.resource.planet.dto.PlanetRequest;
import br.com.paulomoreira.starB2W.util.Constants;
import br.com.paulomoreira.starB2W.util.Converter;
import io.jsonwebtoken.Jwts;
import io.restassured.http.ContentType;

@SpringBootTest(classes = StarB2WApplication.class)
@ActiveProfiles("test")
public class PlanetResourceTest {

	@Autowired
	PlanetResource planetResource;

	@Autowired
	TokenService tokenService;

	@Autowired
	private AuthenticationManager authManager;

	@MockBean
	PlanetService planetService;

	@MockBean
	Converter converter;

	@BeforeEach
	public void setup() {
		standaloneSetup(this.planetResource);
		this.token();
	}
	
	public String token() {
	UsernamePasswordAuthenticationToken loginData = new UsernamePasswordAuthenticationToken(
			"eng.paulomoreira@gmail.com","Paulo1234");
	
	Authentication auth = authManager.authenticate(loginData);
	String token = tokenService.generateToken(auth);
	return token;
	}
	
	@Test
	public void returnSuccess_whenCreatePlanet() throws JsonProcessingException, AttributeException {

		PlanetRequest planetRequest = new PlanetRequest();
		planetRequest.setName("name");
		planetRequest.setClimates(Arrays.asList("climate"));
		planetRequest.setTerrains(Arrays.asList("terrain"));

		Optional<Planet> planet = Optional.of(new Planet());
		planet.get().setId("1");
		planet.get().setMovieAppearances("1");
		planet.get().setName(planetRequest.getName());
		planet.get().setClimates(planetRequest.getClimates());
		planet.get().setTerrains(planetRequest.getTerrains());

		when(this.planetService.createPlanet(planetRequest)).thenReturn(planet);

		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(planetRequest);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", this.token());
		
		given()
		.accept(ContentType.JSON)
		.contentType(ContentType.JSON)
		.headers(httpHeaders)
		.body(body).when()
		.post("/v1/planet").then()
				.statusCode(HttpStatus.CREATED.value());

	}

	@Test
	public void returnBadRequest_whenCreatePlanet_butPlanetExists() throws JsonProcessingException, AttributeException {
		PlanetRequest planetRequest = new PlanetRequest();
		planetRequest.setName("name");
		planetRequest.setClimates(Arrays.asList("arid"));
		planetRequest.setTerrains(Arrays.asList("terrain"));

		when(this.planetService.createPlanet(planetRequest)).thenReturn(null);

		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(planetRequest);

		given().accept(ContentType.JSON).contentType(ContentType.JSON).body(body).when().post("/v1/planet").then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void returnSuccess_whenFindAllPlanets_andExistPlanets() throws JsonProcessingException {

		Optional<Planet> planet = Optional.of(new Planet());
		planet.get().setId("1");
		planet.get().setMovieAppearances("1");
		planet.get().setName("name");
		planet.get().setClimates(Arrays.asList("arid"));
		planet.get().setTerrains(Arrays.asList("terrain"));

		List<Planet> planets = Arrays.asList(planet.get());
		Page<Planet> planetsPage = new PageImpl<Planet>(planets);

		when(this.planetService.findAllPlanets(1)).thenReturn(planetsPage);

		given().accept(ContentType.JSON).when().get("/v1/planets?page=1").then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void returnNotFound_whenFindAll_ButNotExistsPlanets() throws JsonProcessingException {

		Optional<Planet> planet = Optional.of(new Planet());
		planet.get().setId("1");
		planet.get().setMovieAppearances("1");
		planet.get().setName("name");
		planet.get().setClimates(Arrays.asList("arid"));
		planet.get().setTerrains(Arrays.asList("terrain"));

		List<Planet> planets = Arrays.asList();
		Page<Planet> planetsPage = new PageImpl<Planet>(planets);
		when(this.planetService.findAllPlanets(1)).thenReturn(planetsPage);

		given().accept(ContentType.JSON).when().get("/v1/planets?page=1").then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void returnSuccess_whenFindById_AndPlanetExists() throws JsonProcessingException {

		Optional<Planet> planet = Optional.of(new Planet());
		planet.get().setId("1");
		planet.get().setMovieAppearances("1");
		planet.get().setName("name");
		planet.get().setClimates(Arrays.asList("arid"));
		planet.get().setTerrains(Arrays.asList("terrain"));

		when(this.planetService.findPlanetByID("1")).thenReturn(planet);

		given().accept(ContentType.JSON).when().get("/v1/planet/1").then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void returnNotFound_whenFindById_ButPlanetNotExist() throws JsonProcessingException {

		when(this.planetService.findPlanetByID("1")).thenReturn(null);

		given().accept(ContentType.JSON).when().get("/v1/planet/1").then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void returnSuccess_whenFindByName_andPlanetExist() throws JsonProcessingException {

		Optional<Planet> planet = Optional.of(new Planet());
		planet.get().setId("1");
		planet.get().setMovieAppearances("1");
		planet.get().setName("name");
		planet.get().setClimates(Arrays.asList("arid"));
		planet.get().setTerrains(Arrays.asList("terrain"));

		when(this.planetService.findPlanetByID("1")).thenReturn(planet);

		given().accept(ContentType.JSON).when().get("/v1/planet/1").then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void returnNotFound_whenFindByName_andPlanetNotExist() throws JsonProcessingException {

		when(this.planetService.findPlanetByID("1")).thenReturn(null);

		given().accept(ContentType.JSON).when().get("/v1/planet/1").then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void returnSuccess_whenDeletePlanet_andPlanetExist() throws JsonProcessingException {

		when(this.planetService.deletePlanById("1")).thenReturn(true);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", this.token());

		given()
		.accept(ContentType.JSON)
		.headers(httpHeaders)
		.when()
		.delete("/v1/planet/1")
		.then()
		.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void returnNotFound_whenDeletePlanet_andPlanetNotExist() throws JsonProcessingException {

		when(this.planetService.deletePlanById("1")).thenReturn(false);

		

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", this.token());

		given().accept(ContentType.JSON).headers(httpHeaders).when().delete("/v1/planet/1").then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void returnSucess_whenPostPlanetByName_andPlanetNotExist() throws JsonProcessingException {

		Optional<Planet> planet = Optional.of(new Planet());

		when(this.planetService.createPlanetByName("name")).thenReturn(planet);

		given().accept(ContentType.JSON).when().post("/v1/planet/name").then().statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void returnBadRequest_whenPostPlanetByName_andPlanetExist() throws JsonProcessingException {

		Optional<Planet> planet = Optional.empty();

		when(this.planetService.createPlanetByName("name")).thenReturn(planet);

		given().accept(ContentType.JSON).when().post("/v1/planet/name").then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void returnNotFound_whenPostPlanetByName_andPlanetNotFound() throws JsonProcessingException {

		Optional<Planet> planet = null;

		when(this.planetService.createPlanetByName("name")).thenReturn(planet);

		given().accept(ContentType.JSON).when().post("/v1/planet/name").then().statusCode(HttpStatus.NOT_FOUND.value());
	}

};