package br.com.paulomoreira.starB2W.dto;

import br.com.paulomoreira.starB2W.model.Planet;
import lombok.Data;

@Data
public class PlanetResponse {
	
	private String id;
	
	private String name;
	
	private String climate;
	
	private String terrain;
	
	private String movieAppearances;

	public PlanetResponse() {
		super();
	}

	public PlanetResponse(Planet planet) {
		super();
		this.id = planet.getId();
		this.name = planet.getName();
		this.climate = planet.getClimate();
		this.terrain = planet.getTerrain();
	}
	
	
	
}
