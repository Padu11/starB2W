package br.com.paulomoreira.startB2W;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.paulomoreira.startB2W.dto.PlanetRequest;
import br.com.paulomoreira.startB2W.dto.PlanetResponse;
import io.swagger.annotations.Api;

@RestController
@RequestMapping(path = "/v1")
@Api(value = "/v1", tags = "Operations about planet of Star Wars.")
public class PlanetResource {
	
	@Autowired
	PlanetService planetService;
	
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PlanetResponse>> findAllPlanets() {
		
		List<PlanetResponse> planets = planetService.findAllPlanets();
		
		return ResponseEntity.ok().body(planets);
	}
	
	@GetMapping(path = "{name}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<PlanetResponse> findPlanetByName 
	(@PathVariable(value = "name", required = true) String name){
		
		PlanetResponse planetResponse = planetService.findPlanetByName(name);
		
		return ResponseEntity.ok().body(planetResponse);
		
	}
	
	@GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<PlanetResponse> findPlanetById
	(@PathVariable(value = "id", required = true) Long id){
		
		PlanetResponse planetResponse = planetService.findPlanetByID(id);
		
		return ResponseEntity.ok().body(planetResponse);
		
	}
	
	@PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<PlanetResponse> createPlanet(@RequestBody PlanetRequest planetRequest){
		
		PlanetResponse planet = planetService.createPlanet(planetRequest);
		
		return ResponseEntity.created(null).body(planet);	
	}
	
	@DeleteMapping(path = {"uuid"}, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<PlanetResponse> deletePlanetByUuid(@PathVariable (value = "uuid", required = true)
	String uuid){
		
		PlanetResponse planet = planetService.deletePlanByUuid(uuid);
		
		return ResponseEntity.ok(planet);
		
	}
	
	
	

}
