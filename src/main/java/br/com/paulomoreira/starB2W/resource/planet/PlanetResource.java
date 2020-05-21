package br.com.paulomoreira.starB2W.resource.planet;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.paulomoreira.starB2W.exception.AttributeException;
import br.com.paulomoreira.starB2W.exception.ServerTreatment;
import br.com.paulomoreira.starB2W.model.Planet;
import br.com.paulomoreira.starB2W.resource.planet.dto.PlanetPageResponse;
import br.com.paulomoreira.starB2W.resource.planet.dto.PlanetRequest;
import br.com.paulomoreira.starB2W.resource.planet.dto.PlanetResponse;
import br.com.paulomoreira.starB2W.util.Constants;
import br.com.paulomoreira.starB2W.util.Converter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Search Success."),
			@ApiResponse(code = 404, message = "The list of planet is empty.") })
	public ResponseEntity<?> findAllPlanets(@RequestParam(name = "page", required = false) Integer page) {

		try {

			Optional<Page<Planet>> planetsPage = Optional.of(planetService.findAllPlanets(page));

			Integer totalPages = planetsPage.get().getTotalPages();

			Optional<List<Planet>> planets = Optional.of(planetsPage.get().getContent());

			if (planets.get().isEmpty()) {

				HashMap<String, String> body = new HashMap<>();
				body.put(Constants.MESSAGE, Constants.LIST_PLANETS_EMPTY);

				var responseIfplanetsNull = ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);

				return responseIfplanetsNull;

			}

			Optional<PlanetPageResponse> planetsResponse = converter.toPlanetResponse(planets, totalPages);

			var responseIfplanetsNotNull = ResponseEntity.ok().body(planetsResponse);

			return responseIfplanetsNotNull;

		} catch (Exception e) {
			log.error(e.getMessage());
			return ServerTreatment.response();
		}
	}

	@GetMapping(path = "planet", produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get planet by parameter.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Search Success."),
			@ApiResponse(code = 404, message = "The list planet was not found.") })
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
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Search Success."),
			@ApiResponse(code = 404, message = "The list of planet was not found.") })
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
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created Successfully."),
			@ApiResponse(code = 400, message = "The planet exist or some attribute sent is null.") })
	public ResponseEntity<?> createPlanet(@RequestBody @Valid PlanetRequest planetRequest,
			@RequestHeader(value = "Authorization", required = true) String authorization) throws AttributeException {

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

	@PostMapping(path = "planet/{name}", produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create Planet finding in Swapi API by name.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created Successfully."),
			@ApiResponse(code = 400, message = "The planet exist."),
			@ApiResponse(code = 404, message = "The planet was not found.") })
	public ResponseEntity<?> createPlanetByName(@PathVariable(value = "name", required = true) String name) {

		Optional<Planet> planet = planetService.createPlanetByName(name);

		if (planet == null) {
			HashMap<String, String> body = new HashMap<>();
			body.put(Constants.MESSAGE, Constants.PLANET_NOT_FOUND);

			var responseIfPlanetNull = ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);

			return responseIfPlanetNull;

		}
		if (planet.isEmpty()) {

			HashMap<String, String> body = new HashMap<>();
			body.put(Constants.MESSAGE, Constants.PLANET_EXIST);

			var responseIfPlanetWasCreated = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);

			return responseIfPlanetWasCreated;
		}
		Optional<PlanetResponse> planetResponse = converter.toPlanetResponse(planet.get());

		var responseIfPlanetNotNull = ResponseEntity.status(HttpStatus.CREATED).body(planetResponse);

		return responseIfPlanetNotNull;

	}

	@DeleteMapping(path = "planet/{id}", produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Delete Planet by Id.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Deleted Successfully."),
			@ApiResponse(code = 404, message = "The planet was not found.") })
	public ResponseEntity<?> deletePlanetById(@PathVariable(value = "id", required = true) String id,
			@RequestHeader(value = "Authorization", required = true) String authorization) {

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

		}

		catch (Exception e) {
			log.error(e.getMessage());
			return ServerTreatment.response();
		}

	}

}