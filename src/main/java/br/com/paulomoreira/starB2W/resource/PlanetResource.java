package br.com.paulomoreira.starB2W.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.paulomoreira.starB2W.dto.PlanetRequest;
import br.com.paulomoreira.starB2W.dto.PlanetResponse;
import br.com.paulomoreira.starB2W.exception.ServerTreatment;
import br.com.paulomoreira.starB2W.model.Planet;
import br.com.paulomoreira.starB2W.util.Constants;
import br.com.paulomoreira.starB2W.util.Converter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/v1")
@Api(value = "/v1", tags = "Operations about planet of Star Wars.")
@Slf4j
public class PlanetResource {

	@Autowired
	PlanetService planetService;

	@Autowired
	Converter converter;

	@GetMapping(path = "planets", produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get all planets.")
	public ResponseEntity<?> findAllPlanets(@RequestParam(defaultValue = "1", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer size) {

		try {
			Optional<List<Planet>> planets = Optional.of(planetService.findAllPlanets(page, size));

			Optional<List<PlanetResponse>> planetsResponse = converter.toPlanetResponse(planets);

			if (planetsResponse == null) {

				HashMap<String, String> body = new HashMap<>();
				body.put(Constants.MESSAGE, Constants.PLANET_NOT_FOUND);

				var responseIfplanetsNull = ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);

				return responseIfplanetsNull;

			}

			var responseIfplanetsNotNull = ResponseEntity.ok().body(planetsResponse);

			return responseIfplanetsNotNull;

		} catch (Exception e) {
			log.error(e.getMessage());
			return ServerTreatment.response();
		}
	}

	@GetMapping(path = "planet", produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get planet by parameter.")
	public ResponseEntity<?> findPlanetByParameter(@RequestParam(value = "name", required = true) String name) {

		try {
			Optional<Planet> planet = planetService.findPlanetByName(name);

			if (planet == null) {

				HashMap<String, String> body = new HashMap<>();
				body.put(Constants.MESSAGE, Constants.PLANET_NOT_FOUND);

				var responseIfPlanetNull = ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);

				return responseIfPlanetNull;

			}

			Optional<PlanetResponse> planetResponse = converter.toPlanetResponse(planet.get());

			var responseIfPlanetNotNull = ResponseEntity.ok().body(planetResponse);

			return responseIfPlanetNotNull;

		} catch (Exception e) {
			log.error(e.getMessage());
			return ServerTreatment.response();
		}

	}

	@GetMapping(path = "planet/{id}", produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get planet by Id.")
	public ResponseEntity<?> findPlanetById(@PathVariable(value = "id", required = true) String id) {

		try {
			Optional<Planet> planet = planetService.findPlanetByID(id);

			if (planet == null) {

				HashMap<String, String> body = new HashMap<>();
				body.put(Constants.MESSAGE, Constants.PLANET_NOT_FOUND);

				var responseIfPlanetNull = ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);

				return responseIfPlanetNull;
			}

			Optional<PlanetResponse> planetResponse = converter.toPlanetResponse(planet.get());

			var responseIfPlanetNotNull = ResponseEntity.ok().body(planetResponse);

			return responseIfPlanetNotNull;

		} catch (Exception e) {
			log.error(e.getMessage());
			return ServerTreatment.response();
		}

	}

	@PostMapping(path = "planet", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create Planet.")
	public ResponseEntity<?> createPlanet(@RequestBody @Valid PlanetRequest planetRequest) {

		Optional<Planet> planet = planetService.createPlanet(planetRequest);
		try {

			if (planet == null) {

				HashMap<String, String> body = new HashMap<>();
				body.put(Constants.MESSAGE, Constants.PLANET_EXIST);

				var responseIfPlanetNull = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);

				return responseIfPlanetNull;
			}

			Optional<PlanetResponse> planetResponse = converter.toPlanetResponse(planet.get());

			var responseIfPlanetNotNull = ResponseEntity.status(HttpStatus.CREATED).body(planetResponse);

			return responseIfPlanetNotNull;

		} catch (Exception e) {
			log.error(e.getMessage());
			return ServerTreatment.response();
		}

	}

	@DeleteMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Delete Planet by Id.")
	public ResponseEntity<?> deletePlanetById(@RequestParam(value = "id", required = true) String id) {

		try {

			Boolean wasDeleted = planetService.deletePlanById(id);

			if (wasDeleted.equals(Constants.FALSE)) {

				HashMap<String, String> body = new HashMap<>();
				body.put(Constants.MESSAGE, Constants.PLANET_NOT_FOUND);

				var responseIfWasNotDeleted = ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);

				return responseIfWasNotDeleted;
			}

			HashMap<String, String> body = new HashMap<>();
			body.put(Constants.MESSAGE, Constants.PLANET_DELETED);

			var responseIfWasDeleted = ResponseEntity.ok().body(body);

			return responseIfWasDeleted;

		} catch (Exception e) {
			log.error(e.getMessage());
			return ServerTreatment.response();
		}

	}

}