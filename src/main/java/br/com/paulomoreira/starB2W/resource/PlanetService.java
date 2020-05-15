package br.com.paulomoreira.starB2W.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.paulomoreira.starB2W.dto.PlanetRequest;
import br.com.paulomoreira.starB2W.dto.PlanetResponse;
import br.com.paulomoreira.starB2W.model.Planet;
import br.com.paulomoreira.starB2W.util.Constants;
import br.com.paulomoreira.starB2W.util.Converter;
import br.com.paulomoreira.starB2W.util.Validation;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlanetService {

	@Autowired
	PlanetRepository planetRepository;

	@Autowired
	Converter converter;

	@Autowired
	Validation validation;

	public List<PlanetResponse> findAllPlanets() {

		log.info(("Searching all the planets."));
		List<Planet> planets = planetRepository.findAll();
		
		if(planets.isEmpty()) {
			return null;
		}

		return converter.toPlanetsResponse(Optional.of(planets)).get();

	}

	public Optional<List<PlanetResponse>> findPlanetByName(String name) {

		log.info("Searching list of planets.");
		Optional<List<Planet>> planet = planetRepository.findAllByName(name);
		
		if(planet.get().isEmpty()){
			return null;
		}

		return converter.toPlanetsResponse(planet);
	}

	public Optional<PlanetResponse> createPlanet(PlanetRequest planetRequest) {

		String planetName = planetRequest.getName();

		Boolean planetExists = validation.checkIfPlanetExist(planetName);

		if (planetExists == Constants.FALSE) {

			Optional<Planet> planet = converter.requestToPlanet(planetRequest);

			log.info("Creating planet {}.", planet.get().getName());
			planet = Optional.of(planetRepository.save(planet.get()));

			return converter.toPlanetResponse(planet);

		}

		return null;

	}

	public Optional<PlanetResponse> findPlanetByID(String id) {

		log.info("Searching planet with id {}.", id);
		Optional<Planet> planet = planetRepository.findById(id);
		
		if(planet.isEmpty()){
			return null;
		}

		return converter.toPlanetResponse(planet);

	}

	public Boolean deletePlanById(String id) {

		log.info("Deletando planeta com uuid {}.", id);
		Long isDeleted = planetRepository.deleteById(id);

		if (isDeleted == Constants.ONE) {
			return Constants.TRUE;
		}
		return Constants.FALSE;

	}

}
