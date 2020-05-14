package br.com.paulomoreira.starB2W.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlanetRequest {
	
	@ApiModelProperty(position = 1)
	private String name;
	
	@ApiModelProperty(position = 2)
	private String climate;
	
	@ApiModelProperty(position = 3)
	private String ground;

}
