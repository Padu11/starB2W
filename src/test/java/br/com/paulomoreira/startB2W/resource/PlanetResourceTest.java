package br.com.paulomoreira.startB2W.resource;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.paulomoreira.starB2W.StarB2WApplication;
import br.com.paulomoreira.starB2W.dto.PlanetRequest;
import br.com.paulomoreira.starB2W.model.Planet;
import br.com.paulomoreira.starB2W.resource.PlanetResource;
import br.com.paulomoreira.starB2W.resource.PlanetService;
import br.com.paulomoreira.starB2W.util.Converter;
import io.restassured.http.ContentType;

@SpringBootTest(classes = StarB2WApplication.class)
public class PlanetResourceTest {

	@Autowired
	PlanetResource planetResource;

	@MockBean
	PlanetService planetService;

	@MockBean
	Converter converter;

	@BeforeEach
	public void setup() {
		standaloneSetup(this.planetResource);
	}

	@Test
	public void returnSuccess_whenCreatePlanet() throws JsonProcessingException {

		PlanetRequest planetRequest = new PlanetRequest();
		planetRequest.setName("name");
		planetRequest.setClimate("climate");
		planetRequest.setTerrain("terrain");

		Optional<Planet> planet = Optional.of(new Planet());
		planet.get().setId("1");
		planet.get().setMovieAppearances("1");
		planet.get().setName(planetRequest.getName());
		planet.get().setClimate(planetRequest.getClimate());
		planet.get().setTerrain(planetRequest.getTerrain());

		when(this.planetService.createPlanet(planetRequest)).thenReturn(planet);

		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(planetRequest);

		given().accept(ContentType.JSON).contentType(ContentType.JSON).body(body).when().post("/v1/planet").then()
				.statusCode(HttpStatus.CREATED.value());

	}

	@Test
	public void returnBadRequest_whenCreatePlanet_butPlanetExists() throws JsonProcessingException {
		PlanetRequest planetRequest = new PlanetRequest();
		planetRequest.setName("name");
		planetRequest.setClimate("climate");
		planetRequest.setTerrain("terrain");

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
		planet.get().setClimate("climate");
		planet.get().setTerrain("terrain");
		
		
		List<Planet> planets = Arrays.asList(planet.get());

		when(this.planetService.findAllPlanets(10, 1)).thenReturn(planets);

		given()
		.accept(ContentType.JSON)
		.when().get("/v1/planets?page=10&size=1").then()
				.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void returnNotFound_whenFindAll_ButNotExistsPlanets() throws JsonProcessingException {

		Optional<Planet> planet = Optional.of(new Planet());
		planet.get().setId("1");
		planet.get().setMovieAppearances("1");
		planet.get().setName("name");
		planet.get().setClimate("climate");
		planet.get().setTerrain("terrain");
		
		
		List<Planet> planets = Arrays.asList();

		when(this.planetService.findAllPlanets(10, 1)).thenReturn(planets);

		given()
		.accept(ContentType.JSON)
		.when().get("/v1/planets?page=10&size=1").then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void returnSuccess_whenFindById_AndPlanetExists() throws JsonProcessingException {

		Optional<Planet> planet = Optional.of(new Planet());
		planet.get().setId("1");
		planet.get().setMovieAppearances("1");
		planet.get().setName("name");
		planet.get().setClimate("climate");
		planet.get().setTerrain("terrain");
		

		when(this.planetService.findPlanetByID("1")).thenReturn(planet);

		given()
		.accept(ContentType.JSON)
		.when().get("/v1/planet/1").then()
				.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void returnNotFound_whenFindById_ButPlanetNotExist() throws JsonProcessingException {

		when(this.planetService.findPlanetByID("1")).thenReturn(null);

		given()
		.accept(ContentType.JSON)
		.when().get("/v1/planet/1").then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void returnSuccess_whenFindByName_andPlanetExist() throws JsonProcessingException {

		Optional<Planet> planet = Optional.of(new Planet());
		planet.get().setId("1");
		planet.get().setMovieAppearances("1");
		planet.get().setName("name");
		planet.get().setClimate("climate");
		planet.get().setTerrain("terrain");
		

		when(this.planetService.findPlanetByID("1")).thenReturn(planet);

		given()
		.accept(ContentType.JSON)
		.when().get("/v1/planet/1").then()
				.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void returnNotFound_whenFindByName_andPlanetNotExist() throws JsonProcessingException {

		when(this.planetService.findPlanetByID("1")).thenReturn(null);

		given()
		.accept(ContentType.JSON)
		.when().get("/v1/planet/1").then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void returnSuccess_whenDeletePlanet_andPlanetExist() throws JsonProcessingException {

		when(this.planetService.deletePlanById("1")).thenReturn(true);

		given()
		.accept(ContentType.JSON)
		.when().delete("/v1/planet/1").then()
				.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void returnNotFound_whenDeletePlanet_andPlanetNotExist() throws JsonProcessingException {

		when(this.planetService.deletePlanById("1")).thenReturn(false);

		given()
		.accept(ContentType.JSON)
		.when()
		.delete("/v1/planet/1").then()
		.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void returnSucess_whenPostPlanetByName_andPlanetNotExist() throws JsonProcessingException {

		Optional<Planet> planet = Optional.of(new Planet());
		
		when(this.planetService.createPlanetByName("name")).thenReturn(planet);

		given()
		.accept(ContentType.JSON)
		.when()
		.post("/v1/planet/name").then()
		.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void returnBadRequest_whenPostPlanetByName_andPlanetExist() throws JsonProcessingException {

		Optional<Planet> planet = Optional.empty();
		
		when(this.planetService.createPlanetByName("name")).thenReturn(planet);

		given()
		.accept(ContentType.JSON)
		.when()
		.post("/v1/planet/name").then()
		.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void returnNotFound_whenPostPlanetByName_andPlanetNotFound() throws JsonProcessingException {

		Optional<Planet> planet = null;
		
		when(this.planetService.createPlanetByName("name")).thenReturn(planet);

		given()
		.accept(ContentType.JSON)
		.when()
		.post("/v1/planet/name").then()
		.statusCode(HttpStatus.NOT_FOUND.value());
	}
	

};