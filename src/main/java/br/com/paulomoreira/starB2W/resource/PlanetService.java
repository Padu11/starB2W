package br.com.paulomoreira.starB2W.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.paulomoreira.starB2W.dto.PlanetRequest;
import br.com.paulomoreira.starB2W.gateway.SwapiGateway;
import br.com.paulomoreira.starB2W.gateway.dto.SwapiResponse;
import br.com.paulomoreira.starB2W.model.Planet;
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

	public List<Planet> findAllPlanets(Integer page, Integer size) {

		log.info(("Searching all the planets."));
		Pageable pageable = this.creatingPagination(page, size);
		List<Planet> planets = planetRepository.findAll(pageable).getContent();;

		if (planets.isEmpty()) {
			planets = planetRepository.findAll();
			if (planets.isEmpty()) {
				return null;
			}
		}

		return planets;

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

	public Optional<Planet> createPlanet(PlanetRequest planetRequest) {

		String planetName = StringUtils.capitalize(planetRequest.getName());

		Boolean planetExistsInDatebase = validation.checkIfPlanetExistInDatabase(planetName);

		if (planetExistsInDatebase == Constants.FALSE) {

			String movieAppearances = this.checkForMovieAppearances(planetName);

			Optional<Planet> planet = converter.requestToPlanet(planetRequest, movieAppearances);

			log.info("Creating planet {}.", planet.get().getName());
			planet = Optional.of(planetRepository.save(planet.get()));

			return planet;

		}

		return null;

	}

	private String checkForMovieAppearances(String planet) {
		try {
			SwapiResponse response = swapiGateway.findPlanetByName(planet);

			if (response.getResults().isEmpty() || 
					response.getResults() == null) {
				return String.valueOf(Constants.ZERO);
			}

			return String.valueOf(response.getResults().get(0).getFilms().size());
		} catch (FeignException e) {
			log.error(e.getMessage());
			return Validation.movieAppearances(null);
		}

	}

	public Boolean deletePlanById(String id) {

		log.info("Deleting planet with Id{}.", id);
		Long isDeleted = planetRepository.deleteById(id);

		if (isDeleted == Constants.ONE) {
			log.info("Planet with Id {} deleted.", id);
			return Constants.TRUE;
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
