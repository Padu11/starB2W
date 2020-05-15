package br.com.paulomoreira.startB2W;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import br.com.paulomoreira.starB2W.StarB2WApplication;
import br.com.paulomoreira.starB2W.dto.PlanetRequest;
import br.com.paulomoreira.starB2W.dto.PlanetResponse;
import br.com.paulomoreira.starB2W.dto.ResponseDTO;
import br.com.paulomoreira.starB2W.resource.PlanetResource;


@SpringBootTest(classes = StarB2WApplication.class)
@ContextConfiguration
@ActiveProfiles("test")
class StartB2WApplicationTests {
	
	@Autowired
	PlanetResource planetResource;

	@Test
	void contextLoads() {
	
	}
	

	@Test
	public void testCreateAndDeletePlanet() {
	
		PlanetRequest planetRequest = 
				new PlanetRequest("CopaLeme", "Tropical-Praiano", "Rochoso-Arenoso");
	
		ResponseEntity<ResponseDTO> response = planetResource.createPlanet(planetRequest);
				
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		@SuppressWarnings("unchecked")
		Optional<PlanetResponse> planetResponse = (Optional<PlanetResponse>) response.getBody().getResult();
		
		 response = planetResource.deletePlanetById(planetResponse.get().getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void testCreateAndFindPlanetByNameAndDeletePlanet() {
		PlanetRequest planetRequest = 
				new PlanetRequest("CopaLeme", "Tropical-Praiano", "Rochoso-Arenoso");
		ResponseEntity<ResponseDTO> response = planetResource.createPlanet(planetRequest);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		
		@SuppressWarnings("unchecked")
		Optional<PlanetResponse> planetResponse = (Optional<PlanetResponse>) response.getBody().getResult();
		
		String planetName = planetResponse.get().getName();
		
		response = planetResource.findPlanetByName(planetName);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		String planetId = planetResponse.get().getId();
		
		 response = planetResource.deletePlanetById(planetId);
		assertEquals(HttpStatus.OK, response.getStatusCode());	
	}
	
	@Test
	public void testCreateAndFindPlanetByIdAndDeletePlanet() {
		
		PlanetRequest planetRequest = 
				new PlanetRequest("CopaLeme", "Tropical-Praiano", "Rochoso-Arenoso");
		ResponseEntity<ResponseDTO> response = planetResource.createPlanet(planetRequest);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		@SuppressWarnings("unchecked")
		Optional<PlanetResponse> planetResponse = (Optional<PlanetResponse>) response.getBody().getResult();
		
		String planetId = planetResponse.get().getId();
		
		response = planetResource.findPlanetById(planetId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		 response = planetResource.deletePlanetById(planetId);
		assertEquals(HttpStatus.OK, response.getStatusCode());	
		
	}
	

	
	
	
	

	
	

}
