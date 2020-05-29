package br.com.paulomoreira.starB2W.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import br.com.paulomoreira.starB2W.model.Planet;
import br.com.paulomoreira.starB2W.resource.planet.dto.PlanetPageResponse;
import br.com.paulomoreira.starB2W.resource.planet.dto.PlanetRequest;
import br.com.paulomoreira.starB2W.resource.planet.dto.PlanetResponse;

@Component
public class Converter {

	@Autowired
	Validation validation;

	public Optional<PlanetResponse> toPlanetResponse(Optional<Planet> planet, String movieAppearances) {

		if (planet.isPresent()) {
			PlanetResponse planetResponse = new PlanetResponse();

			List<String> climatesLowerCase = this.listToLowerCase(planet.get().getClimates());
			List<String> terrainsLowerCase = this.listToLowerCase(planet.get().getTerrains());

			planetResponse.setId(planet.get().getId());
			planetResponse.setName(planet.get().getName());
			planetResponse.setClimates(climatesLowerCase);
			planetResponse.setTerrains(terrainsLowerCase);

			movieAppearances = Validation.movieAppearances(movieAppearances);
			planetResponse.setMovieAppearances(movieAppearances);

			return Optional.of(planetResponse);
		}

		return null;

	}

	public Optional<PlanetResponse> toPlanetResponse(Planet planet) {

		if (!(planet == null)) {
			PlanetResponse planetResponse = new PlanetResponse();

			List<String> climatesLowerCase = this.listToLowerCase(planet.getClimates());
			List<String> terrainsLowerCase = this.listToLowerCase(planet.getTerrains());
			planetResponse.setId(planet.getId());
			planetResponse.setName(planet.getName());
			planetResponse.setClimates(climatesLowerCase);
			planetResponse.setTerrains(terrainsLowerCase);
			planetResponse.setMovieAppearances(planet.getMovieAppearances());

			return Optional.of(planetResponse);
		}

		return null;

	}

	public Optional<PlanetPageResponse> toPlanetResponse(Optional<List<Planet>> planets, Integer totalPages) {
		
		
		if (planets.isPresent()) {
			
			PlanetPageResponse planetsPage = new PlanetPageResponse();

			List<PlanetResponse> planetsResponse = planets.get().stream().map(PlanetResponse::new)
					.collect(Collectors.toList());
			
			planetsPage.setResult(planetsResponse);
			planetsPage.setTotalPages(totalPages);
			

			return Optional.of(planetsPage);

		}

	return null;

	}

	public Optional<Planet> requestToPlanet(PlanetRequest planetRequest, String movieAppearances) {

		Planet planet = new Planet();

		List<String> climatesLowerCase = this.listToLowerCase(planetRequest.getClimates());
		List<String> terrainLowerCase = this.listToLowerCase(planetRequest.getTerrains());
		
		planet.setName(planetRequest.getName().toLowerCase());
		planet.setTerrains(terrainLowerCase);
		planet.setClimates(climatesLowerCase);
		planet.setMovieAppearances(movieAppearances);

		return Optional.of(planet);
	}

	public List<String> listToLowerCase(List<String> list) {

		List<String> listLowerCase = list.stream().map(String::toLowerCase).collect(Collectors.toList());

		return listLowerCase;
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

	public List<String> climateToList(String climate) {

		climate = climate.replace(", ", ",");

		String[] climates = climate.split(",");

		List<String> climatesArray = Arrays.asList(climates);

		return climatesArray;

	}

	public List<String> terrainsToList(String terrain) {

		terrain = terrain.replace(", ", ",");

		String[] terrains = terrain.split(",");

		List<String> terrainArray = Arrays.asList(terrains);


		return terrainArray;
	}

	public UsernamePasswordAuthenticationToken login(String email, String pass) {
		
		return new UsernamePasswordAuthenticationToken(email, pass);
		
	}

}
