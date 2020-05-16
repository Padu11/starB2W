package br.com.paulomoreira.starB2W.util;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import br.com.paulomoreira.starB2W.dto.ErrorType;
import br.com.paulomoreira.starB2W.dto.ResponseDTO;
import br.com.paulomoreira.starB2W.model.Planet;
import br.com.paulomoreira.starB2W.resource.PlanetRepository;
import br.com.paulomoreira.starB2W.resource.PlanetService;

@Component
public class Validation {

	@Autowired
	PlanetService planetService;

	@Autowired
	Converter converter;
	
	@Autowired
	PlanetRepository planetRepository;

	public static <T> ResponseEntity<ResponseDTO> planetResponse(Optional<T> planetResponse, ResponseDTO bodyIfNull,
			ResponseDTO bodyIfNotNull) {

		if (planetResponse == null) {

			return ResponseEntity.status(bodyIfNull.getStatusCode()).body(bodyIfNull);
		}

		if(bodyIfNotNull.getStatusCode() == HttpStatus.CREATED.value()) {
			return ResponseEntity.created(null).body(bodyIfNotNull);
		}
		
		return ResponseEntity.ok(bodyIfNotNull);

	}

	public Boolean checkIfPlanetExistInDatabase(String planetName) {

		Optional<Planet> planet = planetRepository.findByName(planetName);

		if (planet.isPresent()) {
			return true;
		}
		return false;

	}

	public static ResponseEntity<ResponseDTO> planetDeleted(Boolean wasDeleted, ResponseDTO bodyIfDeleted,
			ResponseDTO bodyIfWasNotDeleted) {

		if (wasDeleted == false) {

			bodyIfWasNotDeleted = ResponseDTO.responseGenerator(HttpStatus.NOT_FOUND.value(),
					Arrays.asList(new ErrorType(Constants.PLANET_NOT_FOUND)));

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bodyIfWasNotDeleted);
		}

		bodyIfDeleted = ResponseDTO.responseGenerator(HttpStatus.OK.value(), Constants.PLANET_DELETED);

		return ResponseEntity.ok(bodyIfDeleted);
	}

	public static String movieAppearances(String movieAppearances) {

		if (movieAppearances == null) {
			return Constants.MOVIE_APPEARANCES_NOT_FOUND;
		}
		return movieAppearances;

	}

}
