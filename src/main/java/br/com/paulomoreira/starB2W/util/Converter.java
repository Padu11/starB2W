package br.com.paulomoreira.starB2W.util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import br.com.paulomoreira.starB2W.dto.PlanetRequest;
import br.com.paulomoreira.starB2W.dto.PlanetResponse;
import br.com.paulomoreira.starB2W.model.Planet;

@Component
public class Converter {

	public Optional<PlanetResponse> toPlanetResponse(Optional<Planet> planet) {
	
		if(planet.isPresent()) {
			PlanetResponse planetResponse = new PlanetResponse();
			
			planetResponse.setId(planet.get().getId());
			planetResponse.setName(planet.get().getName());
			planetResponse.setClimate(planet.get().getClimate());
			planetResponse.setGround(planet.get().getGround());
			
			return Optional.of(planetResponse);
		}
		
		return null;

	}
	
	
	@Validated
	public Optional<Planet> requestToPlanet(PlanetRequest planetRequest) {
		
		Planet planet = new Planet();
		
		planet.setName(planetRequest.getName());
		planet.setGround(planetRequest.getGround());
		planet.setClimate(planetRequest.getClimate());
		
		return Optional.of(planet);	
	}

	public Optional<List<PlanetResponse>> toPlanetsResponse(Optional<List<Planet>> planets) {
		
		if(planets.isPresent()) {
		
		List<PlanetResponse> planetsResponse = 
				planets.get().stream().map(PlanetResponse::new).collect(Collectors.toList());
		
		return Optional.of(planetsResponse);
		
		}
	
		return null;
	}
}
