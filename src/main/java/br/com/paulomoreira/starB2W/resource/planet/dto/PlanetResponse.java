package br.com.paulomoreira.starB2W.resource.planet.dto;

import java.util.List;

import br.com.paulomoreira.starB2W.model.Planet;
import lombok.Data;

@Data
public class PlanetResponse {
	
	private String id;
	
	private String name;
	
	private List<String> climates;
	
	private List<String> terrains;
	
	private String movieAppearances;

	public PlanetResponse() {
		super();
	}

	public PlanetResponse(Planet planet) {
		super();
		this.id = planet.getId();
		this.name = planet.getName();
		this.climates = planet.getClimates();
		this.terrains = planet.getTerrains();
		this.movieAppearances = planet.getMovieAppearances();
	}
	
	
	
}
