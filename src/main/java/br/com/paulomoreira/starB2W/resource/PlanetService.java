package br.com.paulomoreira.starB2W.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.paulomoreira.starB2W.dto.PlanetRequest;
import br.com.paulomoreira.starB2W.dto.PlanetResponse;
import br.com.paulomoreira.starB2W.model.Planet;
import br.com.paulomoreira.starB2W.util.Converter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlanetService {

	@Autowired
	PlanetRepository planetRepository;

	@Autowired
	Converter converter;

	public List<PlanetResponse> findAllPlanets() {

		log.info(("Searching all the planets."));
		List<Planet> planets = planetRepository.findAll();

		return converter.toListOfPlanetResponse(planets);
	}

	public Optional<PlanetResponse> findPlanetByName(String name) {

		log.info("Searching list of planets.");
		Optional<Planet> planet = planetRepository.findByName(name);

		return converter.toPlanetResponse(planet);
	}
	
	public Optional<PlanetResponse> createPlanet(PlanetRequest planetRequest) {
		
		Optional<Planet> planet = converter.requestToPlanet(planetRequest);

		log.info("Creating planet {}", planet.get().getName());
		planet = Optional.of(planetRepository.save(planet.get()));

		return converter.toPlanetResponse(planet);

	}

	public Optional<PlanetResponse> findPlanetByID(String id) {

		log.info("Searching planet with id {}.", id);

		Optional<Planet> planet = planetRepository.findById(id);

		return converter.toPlanetResponse(planet);

	}

	public Boolean deletePlanById(String id) {

		log.info("Deletando planeta com uuid {}.", id);

		 Long isDeleted = planetRepository.deleteById(id);

		 if(isDeleted == 1) {
			 return true;
		 } return false;

	}

}
