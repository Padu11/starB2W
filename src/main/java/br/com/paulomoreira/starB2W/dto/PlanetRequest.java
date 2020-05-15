package br.com.paulomoreira.starB2W.dto;

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
	private String climate;
	
	@ApiModelProperty(position = 3)
	@NotNull
	private String ground;
	
	public PlanetRequest(String name, String climate, String ground) {
		
		this.name = name;
		this.climate = climate;
		this.ground = ground;
		
	}


}
