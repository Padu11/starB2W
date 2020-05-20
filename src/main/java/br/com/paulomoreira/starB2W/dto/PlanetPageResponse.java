package br.com.paulomoreira.starB2W.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"result", "totalPages"})
public class PlanetPageResponse {
	
	@JsonProperty(value = "result")

	List<PlanetResponse> result;
	
	private Integer totalPages;

}
