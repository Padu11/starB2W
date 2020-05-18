package br.com.paulomoreira.startB2W.service;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.paulomoreira.starB2W.StarB2WApplication;
import br.com.paulomoreira.starB2W.dto.PlanetRequest;
import br.com.paulomoreira.starB2W.gateway.SwapiGateway;
import br.com.paulomoreira.starB2W.gateway.dto.SwapiResponse;
import br.com.paulomoreira.starB2W.gateway.dto.SwapiResultResponse;
import br.com.paulomoreira.starB2W.model.Planet;
import br.com.paulomoreira.starB2W.resource.PlanetRepository;
import br.com.paulomoreira.starB2W.resource.PlanetService;
import br.com.paulomoreira.starB2W.util.Converter;
import br.com.paulomoreira.starB2W.util.Validation;

@SpringBootTest(classes = StarB2WApplication.class)
public class PlanetServiceTest {

	@Autowired
	PlanetService planetService;

	@MockBean
	PlanetRepository planetRepository;

	@MockBean
	SwapiGateway swapiGateway;

	@MockBean
	Converter converter;

	@MockBean
	Validation validation;
	

	@BeforeEach
	public void setup() {
		standaloneSetup(this.planetService);
		
		
	}

	@Test
	public void returnSucess_testFindAllPlanet() {

		List<Planet> planets = new ArrayList<>();
		Pageable pageable = PageRequest.of(1, 10);
		Page<Planet> planetPageable = new PageImpl<Planet>(planets);

		when(validation.checkIfPlanetExistInDatabase("Planet")).thenReturn(false);

		when(planetRepository.findAll(pageable)).thenReturn(planetPageable);

		List<Planet> planetsReturned = planetService.findAllPlanets(1, 10);
		assertEquals(planets, planetsReturned);
	}

	@Test
	public void returnSucess_testFindbyName() {

		Planet planet = new Planet();
		planet.setName("name");
		planet.setClimate("climate");
		planet.setTerrain("terrain");

		Optional<Planet> optionalPlanet = Optional.of(planet);

		when(planetRepository.findByName("Name")).thenReturn(optionalPlanet);

		Optional<Planet> planetReturn = planetService.findPlanetByName("Name");

		assertEquals(optionalPlanet, planetReturn);
	}

	@Test
	public void returnSucess_testFindbyId() {

		Planet planet = new Planet();
		planet.setName("name");
		planet.setClimate("climate");
		planet.setTerrain("terrain");

		Optional<Planet> optionalPlanet = Optional.of(planet);

		when(planetRepository.findById("1")).thenReturn(optionalPlanet);
		
		Optional<Planet> planetReturn = planetService.findPlanetByID("1");

		assertEquals(optionalPlanet, planetReturn);
	}
	
	@Test
	public void returnSucess_testCreatePlanet() {
		
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
		
		when(validation.checkIfPlanetExistInDatabase("name")).thenReturn(false);
		when(converter.requestToPlanet(planetRequest, "0")).thenReturn(planet);
		
		when(planetRepository.save(planet.get())).thenReturn(planet.get());
		Optional<Planet> optionalPlanet = planetService.createPlanet(planetRequest);
		
		assertEquals(planet, optionalPlanet);


	}
	
	@Test
	public void returnSucess_testDetelePlanetById() {
		
		when(planetRepository.deleteById("1")).thenReturn(1L);
		
		Boolean wasDeleted = planetService.deletePlanById("1");
		
		assertEquals(true, wasDeleted);

	}
	
	@Test
	public void returnSucess_checkForMovieAppearances() {
		
		Optional<Planet> planet = Optional.of(new Planet());
		planet.get().setId("1");
		planet.get().setMovieAppearances("1");
		planet.get().setName("Name");
		planet.get().setClimate("climate");
		planet.get().setTerrain("terrain");
		
		SwapiResponse swapiresponse = new SwapiResponse();
		List<SwapiResultResponse> result = new ArrayList<>();
		SwapiResultResponse swapi = new SwapiResultResponse();
		result.add(swapi);
		swapiresponse.setResults(result);
		
		when(swapiGateway.findPlanetByName(planet.get().getName())).thenReturn(swapiresponse);
		
		Boolean wasDeleted = planetService.deletePlanById("1");
		
		String movieAppearances = planetService.checkForMovieAppearances("name");
		
		
		assertEquals("0", movieAppearances);

	}

}
