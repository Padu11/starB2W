package br.com.paulomoreira.starB2W.resource.planet.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlanetRequest {
	

	@ApiModelProperty(position = 1)
	@NotNull
	private String name;
	
	@ApiModelProperty(position = 2)
	@NotNull
	private List<String> climates;
	
	@ApiModelProperty(position = 3)
	@NotNull
	private List<String> terrains;
	
	public PlanetRequest() {
	}
	
}
