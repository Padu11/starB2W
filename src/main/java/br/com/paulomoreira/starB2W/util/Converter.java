package br.com.paulomoreira.starB2W.util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.paulomoreira.starB2W.dto.PlanetRequest;
import br.com.paulomoreira.starB2W.dto.PlanetResponse;
import br.com.paulomoreira.starB2W.model.Planet;

@Component
public class Converter {

	@Autowired
	Validation validation;

	public Optional<PlanetResponse> toPlanetResponse(Optional<Planet> planet, String movieAppearances) {

		if (planet.isPresent()) {
			PlanetResponse planetResponse = new PlanetResponse();

			planetResponse.setId(planet.get().getId());
			planetResponse.setName(planet.get().getName());
			planetResponse.setClimate(planet.get().getClimate());
			planetResponse.setTerrain(planet.get().getTerrain());

			movieAppearances = Validation.movieAppearances(movieAppearances);
			planetResponse.setMovieAppearances(movieAppearances);

			return Optional.of(planetResponse);
		}

		return null;

	}

	public Optional<PlanetResponse> toPlanetResponse(Planet planet) {

		if (Optional.of(planet).isPresent()) {
			PlanetResponse planetResponse = new PlanetResponse();

			planetResponse.setId(planet.getId());
			planetResponse.setName(planet.getName());
			planetResponse.setClimate(planet.getClimate());
			planetResponse.setTerrain(planet.getTerrain());
			planetResponse.setMovieAppearances(planet.getMovieAppearances());

			return Optional.of(planetResponse);
		}

		return null;

	}

	public Optional<Planet> requestToPlanet(PlanetRequest planetRequest, String movieAppearances) {

		Planet planet = new Planet();

		planet.setName(planetRequest.getName().toLowerCase());
		planet.setTerrain(planetRequest.getTerrain().toLowerCase());
		planet.setClimate(planetRequest.getClimate().toLowerCase());
		planet.setMovieAppearances(movieAppearances);

		return Optional.of(planet);
	}

	public Optional<List<PlanetResponse>> toPlanetResponse(Optional<List<Planet>> planets) {

		if (planets.isPresent()) {

			List<PlanetResponse> planetsResponse = planets.get().stream().map(PlanetResponse::new)
					.collect(Collectors.toList());

			return Optional.of(planetsResponse);

		}

		return null;
	}

	public Page<PlanetResponse> toPlanetResponse(Page<Planet> planets) {

		if (planets == null) {
			return null;
		}

		Page<PlanetResponse> planetsResponse = planets.map(PlanetResponse::new);

		return planetsResponse;
	}
}
