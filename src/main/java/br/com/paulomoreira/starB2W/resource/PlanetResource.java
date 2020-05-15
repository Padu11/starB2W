package br.com.paulomoreira.starB2W.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.paulomoreira.starB2W.dto.ErrorType;
import br.com.paulomoreira.starB2W.dto.PlanetRequest;
import br.com.paulomoreira.starB2W.dto.PlanetResponse;
import br.com.paulomoreira.starB2W.dto.ResponseDTO;
import br.com.paulomoreira.starB2W.exception.ServerTreatment;
import br.com.paulomoreira.starB2W.util.Constants;
import br.com.paulomoreira.starB2W.util.Validation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/v1")
@Api(value = "/v1/planet", tags = "Operations about planet of Star Wars.")
@Slf4j
public class PlanetResource {

	@Autowired
	PlanetService planetService;

	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get all planets.")
	public ResponseEntity<ResponseDTO> findAllPlanets() {

		try {
			Optional<List<PlanetResponse>> planetsResponse = Optional.of(planetService.findAllPlanets());

			var bodyIfNull = ResponseDTO.responseGenerator(HttpStatus.NOT_FOUND.value(),
					Arrays.asList(new ErrorType(Constants.PLANET_NOT_FOUND)));
			
			var bodyIfNotNull = ResponseDTO.responseGenerator(HttpStatus.OK.value(), planetsResponse);
	
			return Validation.planetResponse(planetsResponse, bodyIfNull, bodyIfNotNull);
			
		} catch (Exception e) {
			log.debug(e.getMessage());
			return ServerTreatment.response();
		}
	}

	@GetMapping(path = "{name}", produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get planet by name.")
	public ResponseEntity<ResponseDTO> findPlanetByName(@RequestParam(value = "name", required = true) String name) {

		try {
			Optional<List<PlanetResponse>> planetResponse = planetService.findPlanetByName(name);

			var bodyIfNull = ResponseDTO.responseGenerator(HttpStatus.NOT_FOUND.value(),
					Arrays.asList(new ErrorType(Constants.PLANET_NOT_FOUND)));

			var bodyIfNotNull = ResponseDTO.responseGenerator(HttpStatus.OK.value(), planetResponse);

			return Validation.planetResponse(planetResponse, bodyIfNull, bodyIfNotNull);

		} catch (Exception e) {
			log.debug(e.getMessage());
			return ServerTreatment.response();
		}

	}

	@GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get planet by Id.")
	public ResponseEntity<ResponseDTO> findPlanetById(@RequestParam(value = "id", required = true) String id) {

		try {
			Optional<PlanetResponse> planetResponse = planetService.findPlanetByID(id);

			var bodyIfNull = ResponseDTO.responseGenerator(HttpStatus.NOT_FOUND.value(),
					Arrays.asList(new ErrorType(Constants.PLANET_NOT_FOUND)));

			var bodyIfNotNull = ResponseDTO.responseGenerator(HttpStatus.OK.value(), planetResponse);

			return Validation.planetResponse(planetResponse, bodyIfNull, bodyIfNotNull);

		} catch (Exception e) {
			log.debug(e.getMessage());
			return ServerTreatment.response();
		}

	}

	@PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create Planet.")
	public ResponseEntity<ResponseDTO> createPlanet(@RequestBody @Validated PlanetRequest planetRequest 
		) {
		
		Optional<PlanetResponse> planetResponse = planetService.createPlanet(planetRequest);
		try {
			
			var bodyIfNull = ResponseDTO.responseGenerator(HttpStatus.NOT_FOUND.value(),
					Arrays.asList(new ErrorType(Constants.PLANET_EXIST)));
			
			var bodyIfNotNull = ResponseDTO.responseGenerator(HttpStatus.CREATED.value(), planetResponse);
			
			return Validation.planetResponse(planetResponse, bodyIfNull, bodyIfNotNull);

		} catch (Exception e) {
			log.debug(e.getMessage());
			return ServerTreatment.response();
		}

	}

	@DeleteMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Delete Planet by Id.")
	public ResponseEntity<ResponseDTO> deletePlanetById(@RequestParam(value = "id", required = true) String id) {

		try {
			
			Boolean wasDeleted = planetService.deletePlanById(id);
			
			var bodyIfWasNotDeleted = ResponseDTO.responseGenerator(
					HttpStatus.NOT_FOUND.value(), Arrays.asList(new ErrorType(Constants.PLANET_NOT_FOUND)));
			
			var bodyIfDeleted = ResponseDTO.responseGenerator(HttpStatus.OK.value(), Constants.PLANET_DELETED);
			
			
			return Validation.planetDeleted(wasDeleted, bodyIfDeleted, bodyIfWasNotDeleted);
			

		} catch (Exception e) {
			log.debug(e.getMessage());
			return ServerTreatment.response();
		}

	}

}
