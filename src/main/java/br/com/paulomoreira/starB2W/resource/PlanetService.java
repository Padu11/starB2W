package br.com.paulomoreira.starB2W.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.paulomoreira.starB2W.dto.PlanetRequest;
import br.com.paulomoreira.starB2W.exception.AttributeException;
import br.com.paulomoreira.starB2W.gateway.SwapiGateway;
import br.com.paulomoreira.starB2W.gateway.dto.SwapiResponse;
import br.com.paulomoreira.starB2W.model.Planet;
import br.com.paulomoreira.starB2W.resource.repository.PlanetRepository;
import br.com.paulomoreira.starB2W.util.Constants;
import br.com.paulomoreira.starB2W.util.Converter;
import br.com.paulomoreira.starB2W.util.Validation;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlanetService {

	@Autowired
	PlanetRepository planetRepository;

	@Autowired
	SwapiGateway swapiGateway;

	@Autowired
	Converter converter;

	@Autowired
	Validation validation;

	public Page<Planet> findAllPlanets(Integer page) {

		log.info(("Searching all the planets."));

		if (page == null) {

			List<Planet> planets = planetRepository.findAll();
			Page<Planet> planetsPage = new PageImpl<Planet>(planets);

			return planetsPage;

		}

		page = page - 1;

		Pageable pageable = this.creatingPagination(page, 10);
		Page<Planet> planetsPage = planetRepository.findAll(pageable);

		if (planetsPage.getContent().isEmpty()) {
			List<Planet> planets = planetRepository.findAll();

			planetsPage = new PageImpl<Planet>(planets);

			if (planets.isEmpty()) {
				return planetsPage;
			}
		}
		return planetsPage;

	}

	public Optional<Planet> findPlanetByName(String name) {

		log.info("Searching for {}.", name);
		Optional<Planet> planet = planetRepository.findByName(name);

		if (planet.isPresent()) {

			return planet;
		}

		return null;

	}

	public Optional<Planet> findPlanetByID(String id) {

		log.info("Searching planet by id {}.", id);
		Optional<Planet> planet = planetRepository.findById(id);

		if (planet.isPresent()) {
			String planetName = planet.get().getName();

			String movieAppearances = this.checkForMovieAppearances(planetName);

			planet.get().setMovieAppearances(movieAppearances);

			return planet;
		}

		return null;

	}

	public Optional<Planet> createPlanet(PlanetRequest planetRequest) throws AttributeException {

		String planetName = StringUtils.capitalize(planetRequest.getName());

		Boolean planetExistsInDatebase = validation.checkIfPlanetExistInDatabaseByName(planetName);

		if (planetExistsInDatebase == Constants.FALSE) {

			String movieAppearances = this.checkForMovieAppearances(planetName);
			Optional<Planet> planet = converter.requestToPlanet(planetRequest, movieAppearances);

			Boolean climateIsValid = validation.climate(planetRequest.getClimates());
			Boolean terrainIsValid = validation.terrain(planetRequest.getTerrains());

			if (climateIsValid == false) {
				throw new AttributeException(Constants.CLIMATE_INVALID);
			}
			if (terrainIsValid == false) {
				throw new AttributeException(Constants.TERRAIN_INVALID);
			}

			log.info("Creating planet {}.", planet.get().getName());
			planet = Optional.of(planetRepository.save(planet.get()));

			return planet;

		}

		return null;

	}

	public Optional<Planet> createPlanetByName(String planetName) {

		Boolean planetExists = validation.checkIfPlanetExistInDatabaseByName(planetName);

		if (planetExists == false) {

			try {
				SwapiResponse response = swapiGateway.findPlanetByName(planetName);

				if (response.getResults().isEmpty()) {
					return null;
				}

				List<String> climates = converter.climateToList(response.getResults().get(0).getClimate());
				List<String> terrains = converter.terrainsToList(response.getResults().get(0).getTerrain());
				Optional<Planet> planet = Optional.of(new Planet());

				planet.get().setName(response.getResults().get(0).getName());
				planet.get().setClimates(climates);
				planet.get().setTerrains(terrains);
				String movieAppearances = String.valueOf(response.getResults().get(0).getFilms().size());
				planet.get().setMovieAppearances(movieAppearances);
				planet = Optional.of(planetRepository.save(planet.get()));

				return planet;

			} catch (FeignException e) {
				log.error(e.getMessage());
				return null;
			}

		}
		Optional<Planet> emptyPlanet = Optional.empty();
		return emptyPlanet;

	}

	public String checkForMovieAppearances(String planet) {
		try {
			SwapiResponse response = swapiGateway.findPlanetByName(planet);

			if (response.getResults().isEmpty() || response.getResults() == null) {
				return String.valueOf(Constants.ZERO);
			}

			return String.valueOf(response.getResults().get(0).getFilms().size());
		} catch (FeignException e) {
			log.error(e.getMessage());
			return Validation.movieAppearances(null);
		}

	}

	public Boolean deletePlanById(String id) {

		Boolean planetExists = validation.checkIfPlanetExistInDatabaseById(id);

		if (planetExists == true) {
			log.info("Deleting planet with Id{}.", id);
			planetRepository.deleteById(id);
		}

		log.info("The planet with Id {} was not found.", id);
		return Constants.FALSE;

	}

	private Pageable creatingPagination(Integer page, Integer size) {

		if (page == null) {
			page = 1;
		}
		if (size == null) {
			size = 10;
		}

		Pageable pageable = PageRequest.of(page, size);

		return pageable;
	}

}
