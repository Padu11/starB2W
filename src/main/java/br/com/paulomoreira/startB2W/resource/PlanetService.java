package br.com.paulomoreira.startB2W;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.paulomoreira.startB2W.dto.PlanetRequest;
import br.com.paulomoreira.startB2W.dto.PlanetResponse;
import br.com.paulomoreira.startB2W.exception.BadRequestException;
import br.com.paulomoreira.startB2W.exception.NotFoundException;
import br.com.paulomoreira.startB2W.model.Planet;
import br.com.paulomoreira.startB2W.util.Constants;
import br.com.paulomoreira.startB2W.util.Converter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlanetService {

	@Autowired
	PlanetRepository planetRepository;

	@Autowired
	Converter converter;

	public PlanetResponse findPlanetByName(String name) {

		log.info("Searching list of planets.");
		Planet planet = planetRepository.findAllByName(name)
				.orElseThrow(() -> new NotFoundException(Constants.PLANET_NOT_FOUND));

		return converter.toPlanetResponse(planet);
	}

	public PlanetResponse createPlanet(PlanetRequest planetRequest) {

		Planet planet = converter.requestToplanet(planetRequest);

		try {
			log.info("Creating planet {}", planet.getName());
			planet = planetRepository.save(planet);
		} catch (Exception e) {
			log.debug("It was not possible to create the new planet. {}", e.getMessage());
			throw new BadRequestException(Constants.PLANET_NOT_CREATED);
		}

		return converter.toPlanetResponse(planet);

	}

	public List<PlanetResponse> findAllPlanets() {
		try {
			List<Planet> planets = planetRepository.findAll();
			return converter.toListOfPlanetResponse(planets);
		} catch (Exception e) {
			throw new BadRequestException(Constants.FIND_ALL_NOT_POSSIBLE);
		}
	}

	public PlanetResponse findPlanetByID(Long id) {

		Planet planet = planetRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(Constants.PLANET_NOT_FOUND));

		return converter.toPlanetResponse(planet);

	}

	public PlanetResponse deletePlanByUuid(String uuid) {

		Planet planet = planetRepository.deleteByUuid().orElseThrow(
				() -> new BadRequestException(Constants.NOT_POSSIBLE_DELETE_PLANET));
		
		return converter.toPlanetResponse(planet);

	}

}
