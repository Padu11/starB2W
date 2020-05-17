package br.com.paulomoreira.starB2W.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.paulomoreira.starB2W.model.Planet;
import br.com.paulomoreira.starB2W.resource.PlanetRepository;

@Component
public class Validation {

	@Autowired
	PlanetRepository planetRepository;



	public Boolean checkIfPlanetExistInDatabase(String planetName) {

		Optional<Planet> planet = planetRepository.findByName(planetName);

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

}