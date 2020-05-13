package br.com.paulomoreira.startB2W.dto;

import br.com.paulomoreira.startB2W.model.Planet;
import lombok.Data;

@Data
public class PlanetResponse {
	
	private String uuid;
	
	private String name;
	
	private String climate;
	
	private String ground;

	public PlanetResponse() {
		super();
	}

	public PlanetResponse(Planet planet) {
		super();
		this.uuid = planet.getUuid();
		this.name = planet.getName();
		this.climate = planet.getClimate();
		this.ground = planet.getGround();
	}
	
	
	
}
