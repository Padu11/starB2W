package br.com.paulomoreira.starB2W.util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.paulomoreira.starB2W.model.Climate;
import br.com.paulomoreira.starB2W.model.Planet;
import br.com.paulomoreira.starB2W.resource.climate.ClimateRepository;
import br.com.paulomoreira.starB2W.resource.planet.PlanetRepository;
import br.com.paulomoreira.starB2W.resource.terrain.TerrainRepository;
import lombok.Data;

@Component
@Data
public class Validation {

	@Autowired
	PlanetRepository planetRepository;

	@Autowired
	ClimateRepository climateRepository;

	@Autowired
	TerrainRepository terrainRepository;

	@Value("${starB2W.jwt.secret}")
	private String secret;

	public Boolean checkIfPlanetExistInDatabaseByName(String planetName) {

		Optional<Planet> planet = planetRepository.findByName(planetName);

		if (planet.isPresent()) {
			return true;
		}
		return false;

	}

	public boolean checkIfPlanetExistInDatabaseById(String id) {
		Optional<Planet> planet = planetRepository.findById(id);

		if (planet.isPresent()) {
			return true;
		}
		return false;

	}

	public static String movieAppearances(String movieAppearances) {

		if (movieAppearances == null) {
			return Constants.MOVIE_APPEARANCES_NOT_FOUND;
		}
		return movieAppearances;

	}

	public Boolean climate(List<String> climates) {

		Collections.sort(climates);

		List<Climate> climatesExisting = climateRepository.findByNameIn(climates);

		List<String> climatesExistingString = climatesExisting.stream().map(c -> c.getName())
				.collect(Collectors.toList());

		Collections.sort(climatesExistingString);

		if (climates.equals((climatesExistingString)) && !(climatesExistingString.isEmpty())) {
			return true;

		}
		return false;

	}

	public Boolean terrain(List<String> terrains) {

		Collections.sort(terrains);

		List<Climate> terrainsExisting = terrainRepository.findByNameIn(terrains);

		List<String> terrainsExistingString = terrainsExisting.stream().map(c -> c.getName())
				.collect(Collectors.toList());

		Collections.sort(terrainsExistingString);

		if (terrains.equals((terrainsExistingString)) && !(terrainsExistingString.isEmpty())) {
			return true;

		}
		return false;

	}

}