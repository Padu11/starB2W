package br.com.paulomoreira.startB2W.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.paulomoreira.startB2W.dto.PlanetRequest;
import br.com.paulomoreira.startB2W.dto.PlanetResponse;
import br.com.paulomoreira.startB2W.model.Planet;

@Component
public class Converter {

	public PlanetResponse toPlanetResponse(Planet planet) {
		
		PlanetResponse planetResponse = new PlanetResponse();
		
		planetResponse.setUuid(planet.getUuid());
		planetResponse.setName(planet.getName());
		planetResponse.setGround(planet.getGround());
		planetResponse.setClimate(planet.getClimate());
		
		return planetResponse;
	}

	public Planet requestToplanet(PlanetRequest planetRequest) {
		
		Planet planet = new Planet();
		
		planet.setName(planetRequest.getName());
		planet.setGround(planetRequest.getGround());
		planet.setClimate(planetRequest.getClimate());
		
		return planet;
		
	}

	public List<PlanetResponse> toListOfPlanetResponse(List<Planet> planets) {
		
		return planets.stream().map(PlanetResponse::new).collect(Collectors.toList());
		
		
		
	}
	
	
	
	

}
